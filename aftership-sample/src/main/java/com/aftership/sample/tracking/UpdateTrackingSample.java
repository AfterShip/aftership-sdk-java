package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.model.tracking.UpdateTracking;

/** Sample of updateTracking method in TrackingEndpoint */
public class UpdateTrackingSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    updateTracking(afterShip);
  }

  public static void updateTracking(AfterShip afterShip) {
    System.out.println(EndpointPath.UPDATE_RETRACK + " updateTracking");

    String id = "vebix4hfu3sr3kac0epve01n";
    UpdateTracking updateTracking = new UpdateTracking();
    updateTracking.setTitle("title123");

    try {
      Tracking tracking1 = afterShip.getTrackingEndpoint().updateTracking(id, updateTracking);
      System.out.println(tracking1);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
