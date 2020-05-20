package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.tracking.MarkAsCompletedRequest;
import com.aftership.sdk.model.tracking.MarkAsCompletedRequest.ReasonKind;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.rest.DataEntity;

/** Sample of markAsCompleted method in TrackingEndpoint */
public class MarkAsCompletedSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    markAsCompleted(afterShip);
  }

  public static void markAsCompleted(AfterShip afterShip) {
    System.out.println(EndpointPath.MARK_AS_COMPLETED);

    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("wpuezshqc272rkaewf2j3019");
    MarkAsCompletedRequest request = new MarkAsCompletedRequest(ReasonKind.LOST);

    DataEntity<SingleTracking> entity =
        afterShip.getTrackingEndpoint().markAsCompleted(param, request);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
      System.out.println(entity.getError().getMessage());
      System.out.println(entity.getError().getCode());
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println(tracking);
    }
  }
}
