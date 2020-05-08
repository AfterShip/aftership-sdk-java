package com.aftership.sample.couriers;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;

public class CourierSample {
    public static void main(String[] args) {
        AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
        ListCouriersSample.listCouriers(afterShip);
        ListAllCouriersSample.listAllCouriers(afterShip);
    }

}
