package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.model.tracking.UpdateTracking;
import com.aftership.sdk.model.tracking.UpdateTrackingRequest;
import com.aftership.sdk.rest.DataEntity;

/** Sample of updateTracking method in TrackingEndpoint */
public class UpdateTrackingSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    updateTracking(afterShip);
  }

  public static void updateTracking(AfterShip afterShip) {
    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("vebix4hfu3sr3kac0epve01n");

    UpdateTracking updateTracking = new UpdateTracking();
    updateTracking.setTitle("title123");
    UpdateTrackingRequest request = new UpdateTrackingRequest();
    request.setTracking(updateTracking);

    DataEntity<SingleTracking> entity =
        afterShip.getTrackingEndpoint().updateTracking(param, request);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println(tracking);
      if (tracking != null) {
        System.out.println(tracking.getTitle());
      }
    }
  }
}
