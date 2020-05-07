package com.aftership.sdk.endpoint;

import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.model.courier.CourierList;

public interface CourierEndpoint {
    DataEntity<CourierList> listCouriers();
    DataEntity<CourierList> listAllCouriers();
}
