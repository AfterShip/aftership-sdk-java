package com.aftership.sdk.endpoint;

import com.aftership.sdk.rest.ApiRequest;

/**
 * AfterShip Endpoint's base class
 */
public abstract class AfterShipEndpoint {
    /**
     * ApiRequest object
     */
    protected final ApiRequest request;

    public AfterShipEndpoint(ApiRequest request) {
        this.request = request;
    }
}
