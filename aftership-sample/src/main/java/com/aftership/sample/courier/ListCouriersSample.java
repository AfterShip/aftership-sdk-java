package com.aftership.sample.courier;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.DataEntity;

/** Sample of listCouriers method in CourierEndpoint */
public class ListCouriersSample {

  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    listCouriers(afterShip);
  }

  public static void listCouriers(AfterShip afterShip) {
    System.out.println(EndpointPath.LIST_COURIERS);
    DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listCouriers();
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
      System.out.println(entity.getError().getCode());
      System.out.println(entity.getError().getMessage());
      System.out.println(entity.getError().getData());
    } else {
      System.out.println(entity.getData().getTotal());
      System.out.println(entity.getData().getCouriers().get(0).getName());
    }

    System.out.println(afterShip.getRateLimit().getReset());
    System.out.println(afterShip.getRateLimit().getLimit());
    System.out.println(afterShip.getRateLimit().getRemaining());
  }
}
