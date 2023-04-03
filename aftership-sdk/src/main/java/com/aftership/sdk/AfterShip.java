package com.aftership.sdk;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.aftership.sdk.auth.AbstractSigner;
import com.aftership.sdk.auth.HMACSigner;
import com.aftership.sdk.endpoint.CheckpointEndpoint;
import com.aftership.sdk.endpoint.CourierEndpoint;
import com.aftership.sdk.endpoint.EstimatedDeliveryDateEndpoint;
import com.aftership.sdk.endpoint.NotificationEndpoint;
import com.aftership.sdk.endpoint.TrackingEndpoint;
import com.aftership.sdk.endpoint.impl.CheckpointImpl;
import com.aftership.sdk.endpoint.impl.CourierImpl;
import com.aftership.sdk.endpoint.impl.EstimatedDeliveryDateImpl;
import com.aftership.sdk.endpoint.impl.NotificationImpl;
import com.aftership.sdk.endpoint.impl.TrackingImpl;
import com.aftership.sdk.exception.ErrorMessage;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.RateLimit;
import com.aftership.sdk.model.RetryOption;
import com.aftership.sdk.request.ApiRequest;
import com.aftership.sdk.request.ApiRequestImpl;
import com.aftership.sdk.auth.AuthenticationType;
import com.aftership.sdk.request.retry.RetryCondition;
import com.aftership.sdk.request.retry.RetryInterceptor;
import com.aftership.sdk.utils.StrUtils;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Entry class Of API function
 */
@Getter
public class AfterShip {
  private static final String DEFAULT_ENDPOINT = "https://api.aftership.com/v4";
  private static final String DEFAULT_USER_AGENT = "aftership-sdk-java";
  /**
   * instance of OkHttpClient
   */
  private final OkHttpClient client;

  /**
   * apiKey parameter in API request
   */
  private final String apiKey;

  /**
   * only when APIKeyType is AES  required
   */
  private final String apiSecret;

  /**
   * only support AES
   */
  private final AbstractSigner signer;

  /**
   * apiKey authentication type in API request signature
   */
  private final AuthenticationType authenticationType;
  /**
   * endpoint parameter in API request
   */
  private final String endpoint;
  /**
   * userAgentPrefix parameter in API request
   */
  private final String userAgentPrefix;

  /**
   * Status of Rate Limit
   */
  @Setter
  private RateLimit rateLimit;

  /**
   * Endpoint of Courier
   */
  private final CourierEndpoint courierEndpoint;
  /**
   * Endpoint of Tracking
   */
  private final TrackingEndpoint trackingEndpoint;
  /**
   * Endpoint of Checkpoint
   */
  private final CheckpointEndpoint checkpointEndpoint;
  /**
   * Endpoint of Notification
   */
  private final NotificationEndpoint notificationEndpoint;
  /**
   * Endpoint of EstimatedDeliveryDate
   */
  private final EstimatedDeliveryDateEndpoint estimatedDeliveryDateEndpoint;

  /**
   * Constructor
   *
   * @param apiKey apiKey parameter in API request
   */
  public AfterShip(String apiKey) {
    this(apiKey, null);
  }

  /**
   * Constructor
   *
   * @param apiKey  apiKey parameter in API request
   * @param options Optional parameters for API request
   */
  public AfterShip(String apiKey, AftershipOption options) {
    this(apiKey, null, null, options);
  }

  /**
   * Constructor
   *
   * @param apiKey    apiKey parameter in API request
   * @param type      apiKey authentication type in API request signature
   * @param apiSecret only when APIKeyType is AES or RSA required
   */
  public AfterShip(String apiKey, AuthenticationType type, String apiSecret) {
    this(apiKey, type, apiSecret, null);
  }

  /**
   * Constructor
   *
   * @param apiKey    apiKey parameter in API request
   * @param type      apiKey authentication type in API request signature
   * @param apiSecret only when APIKeyType is AES or RSA required
   * @param options   Optional parameters for API request
   */
  public AfterShip(String apiKey, AuthenticationType type, String apiSecret, AftershipOption options) {
    if (StrUtils.isBlank(apiKey)) {
      throw new IllegalArgumentException(ErrorMessage.CONSTRUCTOR_API_KEY_IS_NULL);
    }

    this.apiKey = apiKey;
    this.apiSecret = apiSecret;
    this.authenticationType = type;
    this.signer = new HMACSigner(this.apiSecret);


    // http_client
    this.client = createClient(options);

    // Setup
    if (options != null) {
      this.endpoint =
        StrUtils.isNotBlank(options.getEndpoint()) ? options.getEndpoint() : DEFAULT_ENDPOINT;
      this.userAgentPrefix =
        StrUtils.isNotBlank(options.getUserAgentPrefix())
          ? options.getUserAgentPrefix()
          : DEFAULT_USER_AGENT;
    } else {
      this.endpoint = DEFAULT_ENDPOINT;
      this.userAgentPrefix = DEFAULT_USER_AGENT;
    }

    this.rateLimit = new RateLimit(null, null, null);

    final ApiRequest request = new ApiRequestImpl(this);

    // Endpoints
    this.courierEndpoint = new CourierImpl(request);
    this.trackingEndpoint = new TrackingImpl(request);
    this.checkpointEndpoint = new CheckpointImpl(request);
    this.notificationEndpoint = new NotificationImpl(request);
    this.estimatedDeliveryDateEndpoint = new EstimatedDeliveryDateImpl(request);
  }

  /**
   * create a instance of OkHttpClient
   *
   * @param options AftershipOption
   * @return OkHttpClient
   */
  private OkHttpClient createClient(AftershipOption options) {
    OkHttpClient.Builder builder = new OkHttpClient.Builder()
      .callTimeout(getCallTimeout(options), TimeUnit.MILLISECONDS)
      .connectTimeout(getConnectTimeout(options), TimeUnit.MILLISECONDS)
      .readTimeout(getReadTimeout(options), TimeUnit.MILLISECONDS)
      .writeTimeout(getWriteTimeout(options), TimeUnit.MILLISECONDS);


    // set retry interceptor
    RetryOption retryOption = options.getRetryOption();
    if (!Objects.isNull(retryOption)) {
      Interceptor retryInterceptor = new RetryInterceptor(
        getRetryDelay(retryOption),
        getRetryMaxDelay(retryOption),
        getRetryCount(retryOption),
        getRetryConditions(retryOption));
      builder.addInterceptor(retryInterceptor);
    }

    return builder.build();
  }

  private long getCallTimeout(AftershipOption options) {
    if (options == null) {
      return AftershipOption.DEFAULT_TIMEOUT;
    }
    return options.getCallTimeout() >= 0
      ? options.getCallTimeout()
      : AftershipOption.DEFAULT_TIMEOUT;
  }

  private long getConnectTimeout(AftershipOption options) {
    if (options == null) {
      return AftershipOption.DEFAULT_TIMEOUT;
    }
    return options.getConnectTimeout() >= 0
      ? options.getConnectTimeout()
      : AftershipOption.DEFAULT_TIMEOUT;
  }

  private long getReadTimeout(AftershipOption options) {
    if (options == null) {
      return AftershipOption.DEFAULT_TIMEOUT;
    }
    return options.getReadTimeout() >= 0
      ? options.getReadTimeout()
      : AftershipOption.DEFAULT_TIMEOUT;
  }

  private long getWriteTimeout(AftershipOption options) {
    if (options == null) {
      return AftershipOption.DEFAULT_TIMEOUT;
    }
    return options.getWriteTimeout() >= 0
      ? options.getWriteTimeout()
      : AftershipOption.DEFAULT_TIMEOUT;
  }

  private List<RetryCondition> getRetryConditions(RetryOption retryOption) {
    List<RetryCondition> defaultRetryConditions = Arrays.asList(
      (response, exception) -> exception != null || (response != null && response.code() >= 500));

    if (retryOption == null) {
      return defaultRetryConditions;
    }

    return retryOption.getRetryConditions() != null && retryOption.getRetryConditions().size() > 0
      ? retryOption.getRetryConditions()
      : defaultRetryConditions;
  }

  private long getRetryMaxDelay(RetryOption retryOption) {
    return retryOption.getRetryMaxDelay() > 0
      ? retryOption.getRetryMaxDelay()
      : AftershipOption.DEFAULT_RETRY_MAX_DELAY;
  }

  private long getRetryDelay(RetryOption retryOption) {
    return retryOption.getRetryDelay() > 0
      ? retryOption.getRetryDelay()
      : AftershipOption.DEFAULT_RETRY_DELAY;
  }

  private int getRetryCount(RetryOption retryOption) {
    return retryOption.getRetryCount() > 0
      ? retryOption.getRetryCount()
      : AftershipOption.DEFAULT_RETRY_COUNT;
  }


  /**
   * Shutdown the OkHttpClient.
   *
   * @throws IOException when close the client cache.
   */
  public void shutdown() throws IOException {
    if (this.client != null) {
      this.client.dispatcher().executorService().shutdown();
      this.client.connectionPool().evictAll();
      Cache cache = this.client.cache();
      if (cache != null) {
        cache.close();
      }
    }
  }
}
