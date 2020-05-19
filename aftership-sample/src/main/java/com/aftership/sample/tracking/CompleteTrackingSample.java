package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.tracking.CompleteTrackingRequest;
import com.aftership.sdk.model.tracking.CompleteTrackingRequest.ReasonKind;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.rest.DataEntity;

/** Sample of completeTracking method in TrackingEndpoint */
public class CompleteTrackingSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    completeTracking(afterShip);
  }

  public static void completeTracking(AfterShip afterShip) {
    System.out.println(EndpointPath.CREATE_TRACKING);

    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("wcwy86mie4o17kadedkcw029");
    CompleteTrackingRequest request = new CompleteTrackingRequest(ReasonKind.LOST);

    DataEntity<SingleTracking> entity =
        afterShip.getTrackingEndpoint().completeTracking(param, request);
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
