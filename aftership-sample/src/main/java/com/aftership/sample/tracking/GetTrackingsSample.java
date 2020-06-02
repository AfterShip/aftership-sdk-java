package com.aftership.sample.tracking;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.tracking.GetTrackingsParams;
import com.aftership.sdk.model.tracking.PagedTrackings;

/** Sample of getTrackings method in TrackingEndpoint */
public class GetTrackingsSample {

  public static void main(String[] args) throws SdkException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    getTrackings(afterShip);
  }

  public static void getTrackings(AfterShip afterShip) {
    System.out.println(EndpointPath.GET_TRACKING);

    GetTrackingsParams optionalParams = new GetTrackingsParams();
    optionalParams.setFields("title,order_id");
    optionalParams.setLang("china-post");
    optionalParams.setLimit(10);

    try {
      PagedTrackings pagedTrackings = afterShip.getTrackingEndpoint().getTrackings(optionalParams);
      System.out.println(pagedTrackings);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
