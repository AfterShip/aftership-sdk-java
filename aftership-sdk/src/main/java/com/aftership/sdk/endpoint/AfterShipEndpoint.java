package com.aftership.sdk.endpoint;

import com.aftership.sdk.rest.ApiRequest;

public abstract class AfterShipEndpoint {
    protected final ApiRequest request;

    public AfterShipEndpoint(ApiRequest request) {
        this.request = request;
    }
}
