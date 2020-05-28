package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.model.tracking.CompletedStatus;
import com.aftership.sdk.model.tracking.CompletedStatus.ReasonKind;
import com.aftership.sdk.model.tracking.Tracking;

/** Sample of markAsCompleted method in TrackingEndpoint */
public class MarkAsCompletedSample {
  public static void main(String[] args) throws ConstructorException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    markAsCompleted(afterShip);
  }

  public static void markAsCompleted(AfterShip afterShip) {
    System.out.println(EndpointPath.MARK_AS_COMPLETED);

    String id = "5b7658cec7c33c0e007de3c5";
    try {
      Tracking tracking =
          afterShip.getTrackingEndpoint().markAsCompleted(id, new CompletedStatus(ReasonKind.LOST));
      System.out.println(tracking);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
