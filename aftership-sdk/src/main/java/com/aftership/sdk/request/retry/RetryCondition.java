package com.aftership.sdk.request.retry;

import okhttp3.Response;

import java.io.IOException;

/**
 * A functional interface used to define custom retry conditions for the RetryInterceptor.
 */
@FunctionalInterface
public interface RetryCondition {
  /**
   * Determines whether a request should be retried based on the given response and exception (if any).
   *
   * @param response  The response from the previous retry attempt, or null if this is the first attempt.
   * @param exception The exception thrown during the previous retry attempt, or null if there was no exception.
   * @return true if the request should be retried; false otherwise.
   */
  boolean shouldRetry(Response response, IOException exception);
}
