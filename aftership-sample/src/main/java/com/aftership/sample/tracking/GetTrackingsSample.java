package com.aftership.sample.tracking;

import java.util.List;
import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.model.tracking.MultiTrackingsData;
import com.aftership.sdk.model.tracking.MultiTrackingsParams;
import com.aftership.sdk.model.tracking.MultiTrackingsParams.FieldsKind;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.rest.DataEntity;

/** Sample of getTrackings method in TrackingEndpoint */
public class GetTrackingsSample {

  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    getTrackings(afterShip);
  }

  public static void getTrackings(AfterShip afterShip) {
    MultiTrackingsParams optionalParams = new MultiTrackingsParams();
    optionalParams.setFields(FieldsKind.combine(FieldsKind.ORDER_ID, FieldsKind.TAG));
    optionalParams.setLimit(2);

    DataEntity<MultiTrackingsData> entity =
        afterShip.getTrackingEndpoint().getTrackings(optionalParams);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      List<Tracking> trackings = entity.getData().getTrackings();
      System.out.println("size: " + trackings.size());
      System.out.println(trackings);
    }

    // handle Rate Limiter.
    System.out.println(afterShip.getRateLimit().getReset());
    System.out.println(afterShip.getRateLimit().getLimit());
    System.out.println(afterShip.getRateLimit().getRemaining());
  }
}
