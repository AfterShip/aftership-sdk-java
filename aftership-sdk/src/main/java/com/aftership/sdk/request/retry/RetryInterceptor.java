package com.aftership.sdk.request.retry;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * An OkHttp interceptor that retries failed requests according to customizable retry conditions.
 */
public class RetryInterceptor implements Interceptor {

  private long initialDelay;              // Initial retry delay in milliseconds
  private long maxDelay;                  // Maximum retry delay in milliseconds
  private int maxRetries;                 // Maximum number of retries
  private double backoffMultiplier;       // Exponential backoff multiplier. Should ideally be greater than 1.
  private double jitterFactor;            // Jitter factor for randomized delay
  private List<RetryCondition> retryConditions;  // Customizable retry condition

  /**
   * Constructs a new RetryInterceptor with the specified parameters.
   */
  public RetryInterceptor(long initialDelay,
                          long maxDelay,
                          int maxRetries,
                          double backoffMultiplier,
                          double jitterFactor,
                          List<RetryCondition> retryConditions) {
    this.initialDelay = initialDelay;
    this.maxDelay = maxDelay;
    this.maxRetries = maxRetries;
    this.backoffMultiplier = backoffMultiplier;
    this.jitterFactor = jitterFactor;
    this.retryConditions = retryConditions;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    return retryWithBackoff(chain);
  }

  /**
   * Retries the request with exponential backoff and randomized jitter between retries, as configured by
   * the retry parameters. This method is used internally by the interceptor.
   */
  protected Response retryWithBackoff(Chain chain) throws IOException {
    Response response = null;
    IOException exception = null;
    // Iterate through the retry retries
    // The first time is the normal request, and only after that is the number of retries ons
    for (int retries = 0; retries <= maxRetries; retries++) {
      try {
        response = chain.proceed(chain.request());

        if (response.isSuccessful()) {
          return response;
        }
      } catch (IOException e) {
        exception = e;
      }

      // If the custom retry condition is met, wait for the delay
      if (shouldRetry(response, exception)) {
        waitForDelay(retries);
      }
    }

    if (exception != null) {
      throw exception;
    } else {
      return response;
    }
  }

  /**
   * Checks whether the interceptor should retry the request based on the specified custom retry conditions,
   * and the given response and exception (if any). Returns true if the request should be retried; false otherwise.
   */
  private boolean shouldRetry(Response response, IOException exception) {
    for (RetryCondition condition : retryConditions) {
      if (condition.shouldRetry(response, exception)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Calculates and waits for the delay between retries, using exponential backoff with randomized jitter.
   *
   * @param retries The current retry attempt number.
   */
  private void waitForDelay(int retries) {
    long delay = backoff(retries);
    try {
      TimeUnit.MILLISECONDS.sleep(delay);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Calculates the exponential backoff delay for the specified retry attempt number, using the configured
   * backoff multiplier and maximum delay, and adds randomized jitter to the delay.
   *
   * @param retries The current retry attempt number.
   * @return The calculated delay before the next retry attempt, in milliseconds.
   */
  private long backoff(int retries) {
    // Calculate exponential backoff delay: initialDelay * (backoffMultiplier ^ retries)
    long delay = (long) (initialDelay * Math.pow(backoffMultiplier, retries));
    // Calculate jitter: jitterFactor * delay * random number in [-1, 1) range
    double jitter = jitterFactor * delay * (Math.random() - 0.5) * 2;
    // Add the exponential backoff delay and jitter to get the final delay
    long finalDelay = (long) (delay + jitter);

    // Ensure the final delay does not exceed the maximum delay
    if (finalDelay > maxDelay) {
      finalDelay = maxDelay;
    }

    return finalDelay;
  }

}
