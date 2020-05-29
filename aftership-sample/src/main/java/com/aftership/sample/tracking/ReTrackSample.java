package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.tracking.Tracking;

/** Sample of reTrack method in TrackingEndpoint */
public class ReTrackSample {

  public static void main(String[] args) throws SdkException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    reTrack(afterShip);
  }

  public static void reTrack(AfterShip afterShip) {
    System.out.println("reTrack");

    String id = "l389dilsluk9ckaqmetr901y";
    try {
      Tracking tracking = afterShip.getTrackingEndpoint().reTrack(id);
      System.out.println(tracking);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
