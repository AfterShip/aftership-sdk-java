package com.aftership.sample.courier;

import java.util.Arrays;
import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
import com.aftership.sdk.model.courier.CourierDetectTracking;
import com.aftership.sdk.rest.DataEntity;

/** Sample of detectCouriers method in CourierEndpoint */
public class DetectCouriersSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    detectCouriers(afterShip);
  }

  public static void detectCouriers(AfterShip afterShip) {
    System.out.println(EndpointPath.DETECT_COURIERS);

    CourierDetectTracking tracking = new CourierDetectTracking();
    tracking.setTrackingNumber("906587618687");
    CourierDetectRequest courierDetectRequest = new CourierDetectRequest(tracking);

    DataEntity<CourierDetectList> entity =
        afterShip.getCourierEndpoint().detectCouriers(courierDetectRequest);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      System.out.println(entity.getData().getTotal());
      System.out.println(entity.getData().getCouriers());
    }

    System.out.println(afterShip.getRateLimit().getReset());
    System.out.println(afterShip.getRateLimit().getLimit());
    System.out.println(afterShip.getRateLimit().getRemaining());
  }
}
