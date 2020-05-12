package com.aftership.sdk;

import com.aftership.sdk.endpoint.CourierEndpoint;
import com.aftership.sdk.endpoint.TrackingEndpoint;
import com.aftership.sdk.error.AftershipException;
import com.aftership.sdk.error.ErrorMessage;
import com.aftership.sdk.impl.CourierImpl;
import com.aftership.sdk.impl.TrackingImpl;
import com.aftership.sdk.lib.StrUtil;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.RateLimit;
import com.aftership.sdk.rest.ApiRequest;
import com.aftership.sdk.rest.ApiRequestImpl;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AfterShip {
    public static final String DEFAULT_ENDPOINT = "https://api.aftership.com/v4";
    public static final String DEFAULT_USER_AGENT = "aftership-sdk-java";

    private String apiKey;
    private String endpoint;
    private String userAgentPrefix;

    @Setter
    private RateLimit rateLimit;

    private CourierEndpoint courierEndpoint;
    private TrackingEndpoint trackingEndpoint;

    public AfterShip(String apiKey) {
        this(apiKey, null);
    }

    public AfterShip(String apiKey, AftershipOption options) {
        if (StrUtil.isBlank(apiKey)) {
            throw new AftershipException(ErrorMessage.CONSTRUCTOR_INVALID_API_KEY);
        }

        this.apiKey = apiKey;

        // Setup
        if (options != null) {
            this.endpoint = StrUtil.isNotBlank(options.getEndpoint())
                    ? options.getEndpoint()
                    : DEFAULT_ENDPOINT;
            this.userAgentPrefix = StrUtil.isNotBlank(options.getUserAgentPrefix())
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
    }

}
