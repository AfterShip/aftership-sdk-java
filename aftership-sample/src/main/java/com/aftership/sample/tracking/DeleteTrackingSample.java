package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.rest.DataEntity;

/** Sample of deleteTracking method in TrackingEndpoint */
public class DeleteTrackingSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    deleteTracking(afterShip);
  }

  public static void deleteTracking(AfterShip afterShip) {
    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("wpuezshqc272rkaewf2j3019");

    DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().deleteTracking(param);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
      System.out.println(entity.getError().getMessage());
      System.out.println(entity.getError().getCode());
      System.out.println(entity.getError().getData());
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println(tracking);
    }
  }
}
