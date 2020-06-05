package com.aftership.sdk;

import com.aftership.sdk.endpoint.CheckpointEndpoint;
import com.aftership.sdk.endpoint.CourierEndpoint;
import com.aftership.sdk.endpoint.NotificationEndpoint;
import com.aftership.sdk.endpoint.TrackingEndpoint;
import com.aftership.sdk.endpoint.impl.CheckpointImpl;
import com.aftership.sdk.endpoint.impl.CourierImpl;
import com.aftership.sdk.endpoint.impl.NotificationImpl;
import com.aftership.sdk.endpoint.impl.TrackingImpl;
import com.aftership.sdk.exception.ErrorMessage;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.RateLimit;
import com.aftership.sdk.request.ApiRequest;
import com.aftership.sdk.request.ApiRequestImpl;
import com.aftership.sdk.utils.StrUtils;
import lombok.Getter;
import lombok.Setter;

/** Entry class Of API function */
@Getter
public class AfterShip {
  private static final String DEFAULT_ENDPOINT = "https://api.aftership.com/v4";
  private static final String DEFAULT_USER_AGENT = "aftership-sdk-java";

  /** apiKey parameter in API request */
  private final String apiKey;
  /** endpoint parameter in API request */
  private final String endpoint;
  /** userAgentPrefix parameter in API request */
  private final String userAgentPrefix;

  /** Status of Rate Limit */
  @Setter private RateLimit rateLimit;

  /** Endpoint of Courier */
  private final CourierEndpoint courierEndpoint;
  /** Endpoint of Tracking */
  private final TrackingEndpoint trackingEndpoint;
  /** Endpoint of Checkpoint */
  private final CheckpointEndpoint checkpointEndpoint;
  /** Endpoint of Notification */
  private final NotificationEndpoint notificationEndpoint;

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
   * @param apiKey apiKey parameter in API request
   * @param options Optional parameters for API request
   */
  public AfterShip(String apiKey, AftershipOption options) {
    if (StrUtils.isBlank(apiKey)) {
      throw new IllegalArgumentException(ErrorMessage.CONSTRUCTOR_API_KEY_IS_NULL);
    }

    this.apiKey = apiKey;

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
  }
}
