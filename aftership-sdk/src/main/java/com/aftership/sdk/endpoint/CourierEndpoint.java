package com.aftership.sdk.endpoint;

import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.DataEntity;

public interface CourierEndpoint {
    DataEntity<CourierList> listCouriers();
    DataEntity<CourierList> listAllCouriers();
    DataEntity<CourierDetectList> detectCouriers(CourierDetectRequest requestData);
}
