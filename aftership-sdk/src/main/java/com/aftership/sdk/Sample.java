package com.aftership.sdk;

import java.util.List;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.model.tracking.MultiTrackingsData;
import com.aftership.sdk.model.tracking.MultiTrackingsParams;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.rest.DataEntity;

public class Sample {

  public static void main(String[] args) {
    AfterShip afterShip =
        new AfterShip("YOUR_API_KEY", new AftershipOption("http://localhost:8080/v4"));
    test2(afterShip);
  }

  private static void  test1(AfterShip afterShip){
    DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listCouriers();
    if (entity.hasError()) {
      // handle Error.
      System.out.println(entity.getError().getType());
      if (entity.getError().isApiError()) {
        System.out.println(entity.getError().getCode());
      }
      System.out.println(entity.getError().getData().get("requestConfig"));
      System.out.println(entity.getError().getData().get("requestHeaders"));
      System.out.println(entity.getError().getData().get("requestData"));
      System.out.println(entity.getError().getData().get("responseBody"));
    } else {
      // handle Data
      System.out.println(entity.getData());
    }
    // handle Rate Limiter.
    System.out.println(afterShip.getRateLimit().getReset());
    System.out.println(afterShip.getRateLimit().getLimit());
    System.out.println(afterShip.getRateLimit().getRemaining());
  }

  private static void  test2(AfterShip afterShip){
    MultiTrackingsParams optionalParams = new MultiTrackingsParams();
    optionalParams.setFields("title,order_id");
    optionalParams.setLang("china-post");
    optionalParams.setLimit(10);

    DataEntity<MultiTrackingsData> entity = afterShip.getTrackingEndpoint().getTrackings(optionalParams);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
      System.out.println(entity.getError().getCode());
      System.out.println(entity.getError().getMessage());
      System.out.println(entity.getError().getData());
    } else {
      List<Tracking> trackings = entity.getData().getTrackings();
      System.out.println(trackings);
    }
  }


}
