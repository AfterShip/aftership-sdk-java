package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.rest.DataEntity;

/** Sample of reTrack method in TrackingEndpoint */
public class ReTrackSample {

  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    reTrack(afterShip);
  }

  public static void reTrack(AfterShip afterShip) {
    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("vebix4hfu3sr3kac0epve01n");

    DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().reTrack(param);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
      System.out.println(entity.getError().getCode());
      System.out.println(entity.getError().getMessage());
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println(tracking);
      if (tracking != null) {
        System.out.println(tracking.isActive());
      }
    }
  }
}
