package com.aftership.sample;

import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.DataEntity;

public class ListAllCouriersSample {

    public static void main(String[] args) {
        AftershipOption option = new AftershipOption();
        option.setEndpoint("http://localhost:8080");

        AfterShip afterShip = new AfterShip("YOUR_API_KEY", option);

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
