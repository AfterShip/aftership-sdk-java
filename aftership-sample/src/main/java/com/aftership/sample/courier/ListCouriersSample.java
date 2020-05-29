package com.aftership.sample.courier;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.courier.CourierList;

/** Sample of listCouriers method in CourierEndpoint */
public class ListCouriersSample {

  public static void main(String[] args) throws SdkException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    listCouriers(afterShip);
  }

  public static void listCouriers(AfterShip afterShip) {
    System.out.println(EndpointPath.LIST_COURIERS);

    try {
      CourierList courierList = afterShip.getCourierEndpoint().listCouriers();
      System.out.println(courierList.getTotal());
      System.out.println(courierList.getCouriers().get(0).getName());
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }

    System.out.println(afterShip.getRateLimit().getReset());
    System.out.println(afterShip.getRateLimit().getLimit());
    System.out.println(afterShip.getRateLimit().getRemaining());
  }
}
