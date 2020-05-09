package com.aftership.sdk.impl;

import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.CourierEndpoint;
import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
import com.aftership.sdk.rest.*;
import com.aftership.sdk.model.courier.CourierList;

public class CourierImpl extends AfterShipEndpoint implements CourierEndpoint {
    public CourierImpl(ApiRequest request) {
        super(request);
    }

    @Override
    public DataEntity<CourierList> listCouriers() {
        return this.request.makeRequest(new RequestConfig(HttpMethod.GET, EndpointPath.LIST_COURIERS),
                null, CourierList.class);
    }

    @Override
    public DataEntity<CourierList> listAllCouriers() {
        return this.request.makeRequest(new RequestConfig(HttpMethod.GET, EndpointPath.LIST_ALL_COURIERS),
                null, CourierList.class);
    }

    @Override
    public DataEntity<CourierDetectList> detectCouriers(CourierDetectRequest requestData) {
        return this.request.makeRequest(new RequestConfig(HttpMethod.POST, EndpointPath.DETECT_COURIERS),
                requestData, CourierDetectList.class);
    }

}
