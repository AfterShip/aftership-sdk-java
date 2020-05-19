package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.rest.DataEntity;

/** Sample of getTracking method in TrackingEndpoint */
public class GetTrackingSample {

  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    getTracking(afterShip);
    getTracking2(afterShip);
  }

  public static void getTracking(AfterShip afterShip) {
    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("vebix4hfu3sr3kac0epve01n");

    DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().getTracking(param, null);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println(tracking);
      if (tracking != null) {
        System.out.println(tracking.getSlug());
        System.out.println(tracking.getTrackingNumber());
        System.out.println("title:" + tracking.getTitle());
      }
    }
  }

  public static void getTracking2(AfterShip afterShip) {
    SingleTrackingParam param = new SingleTrackingParam();
    param.setSlug("dhl");
    param.setTrackingNumber("1234567890");

    DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().getTracking(param, null);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println(tracking);
      if (tracking != null) {
        System.out.println(tracking.getId());
      }
    }
  }
}
