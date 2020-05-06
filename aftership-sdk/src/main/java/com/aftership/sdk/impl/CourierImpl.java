package com.aftership.sdk.impl;

import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.CourierEndpoint;
import com.aftership.sdk.rest.*;
import com.aftership.sdk.model.courier.CourierList;

public class CourierImpl extends AfterShipEndpoint implements CourierEndpoint  {

    public CourierImpl(ApiRequest request) {
        super(request);
    }

    @Override
    public DataEntity<CourierList> listCouriers() {
        return this.request.makeRequest(new RequestConfig(HttpMethod.GET, "/couriers"),
                null, CourierList.class);
    }

}
