package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.model.tracking.Tracking;

/** Sample of getTracking method in TrackingEndpoint */
public class GetTrackingSample {

  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    getTracking(afterShip);
    getTracking2(afterShip);
  }

  public static void getTracking(AfterShip afterShip) {
    System.out.println(EndpointPath.GET_TRACKING + " by id");

    String id = "l389dilsluk9ckaqmetr901y";
    try {
      Tracking tracking = afterShip.getTrackingEndpoint().getTracking(id, null);
      System.out.println(tracking);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void getTracking2(AfterShip afterShip) {
    System.out.println(EndpointPath.GET_TRACKING + " by slug and trackingNumber");

    String slug = "acommerce";
    String trackingNumber = "1234567890";
    try {
      Tracking tracking =
          afterShip
              .getTrackingEndpoint()
              .getTracking(new SlugTrackingNumber(slug, trackingNumber), null);
      System.out.println(tracking);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
