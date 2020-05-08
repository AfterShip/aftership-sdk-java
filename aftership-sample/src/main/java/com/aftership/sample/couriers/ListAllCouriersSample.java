package com.aftership.sample.couriers;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.DataEntity;

public class ListAllCouriersSample {

    public static void main(String[] args) {
        AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
        listAllCouriers(afterShip);
    }

    public static void listAllCouriers(AfterShip afterShip) {
        System.out.println(EndpointPath.LIST_ALL_COURIERS);
        DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listAllCouriers();
        if (entity.hasError()) {
            System.out.println(entity.getError());
        } else {
            System.out.println(entity.getData());
        }
        System.out.println(afterShip.getRateLimit().toString());
    }

}
