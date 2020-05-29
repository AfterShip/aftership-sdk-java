package com.aftership.sample.courier;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
import com.aftership.sdk.model.courier.CourierDetectTracking;

/** Sample of detectCouriers method in CourierEndpoint */
public class DetectCouriersSample {
  public static void main(String[] args) throws SdkException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    detectCouriers(afterShip);
  }

  public static void detectCouriers(AfterShip afterShip) {
    System.out.println(EndpointPath.DETECT_COURIERS);

    CourierDetectTracking tracking = new CourierDetectTracking();
    tracking.setTrackingNumber("906587618687");
    CourierDetectRequest courierDetectRequest = new CourierDetectRequest(tracking);

    try {
      CourierDetectList courierDetectList =
          afterShip.getCourierEndpoint().detectCouriers(courierDetectRequest.getTracking());

      System.out.println(courierDetectList.getTotal());
      System.out.println(courierDetectList.getCouriers());

    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
