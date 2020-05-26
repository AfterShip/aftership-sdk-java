package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.error.AftershipError;
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
    System.out.println("======== get by id ========");
    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("vebix4hfu3sr3kac0epve01n");

    DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().getTracking(param, null);
    if (entity.hasError()) {
      System.out.println("type: " + entity.getError().getType());
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println("tracking: " + tracking);
      if (tracking != null) {
        System.out.println("slug: " + tracking.getSlug());
        System.out.println("tracking_number: " + tracking.getTrackingNumber());
        System.out.println("title: " + tracking.getTitle());
      }
    }
  }

  public static void getTracking2(AfterShip afterShip) {
    System.out.println("======== get by slug and tracking_number ========");
    SingleTrackingParam param = new SingleTrackingParam();
    param.setSlug("mx-cargo");
    param.setTrackingNumber("1234567890");

    DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().getTracking(param, null);
    if (entity.hasError()) {
      AftershipError error = entity.getError();
      System.out.println("type: " + error.getType());
      if (error.isApiError()) {
        System.out.println("code: " + error.getCode());
        System.out.println("message: " + error.getMessage());
        error.printData();
      }
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println(tracking);
      if (tracking != null) {
        System.out.println("id: " + tracking.getId());
      }
    }
  }
}
