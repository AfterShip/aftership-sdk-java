package com.aftership.sdk.endpoint;

import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.model.courier.CourierList;

public interface CourierEndpoint {
    DataEntity<CourierList> listCouriers();
    DataEntity<CourierList> listAllCouriers();
    DataEntity<CourierDetectList> detectCouriers(CourierDetectRequest requestData);
}
