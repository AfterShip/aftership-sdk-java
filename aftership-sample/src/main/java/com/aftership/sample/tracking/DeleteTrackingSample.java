package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.tracking.Tracking;

/** Sample of deleteTracking method in TrackingEndpoint */
public class DeleteTrackingSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    deleteTracking(afterShip);
  }

  public static void deleteTracking(AfterShip afterShip) {
    System.out.println(EndpointPath.DELETE_TRACKING);

    String id = "u2qm5uu9xqpwykaqm8d5l010";
    try {
      Tracking tracking = afterShip.getTrackingEndpoint().deleteTracking(id);
      System.out.println(tracking);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
