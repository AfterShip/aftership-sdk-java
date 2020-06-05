package com.aftership.sample;

import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;

/** Test for Shutdown OkHttpClient. example: kill pid; kill -2 pid; kill -15 pid; */
public class Shutdown {

  public static void main(String[] args) {
    AftershipOption option = new AftershipOption();
    option.setEndpoint("http://localhost:8080/v4");
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), option);

    int nums = 10000;
    for (int i = 0; i < nums; i++) {
      listCouriers(afterShip);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    try {
      Thread.sleep(100000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static void listCouriers(AfterShip afterShip) {
    System.out.println(EndpointPath.LIST_COURIERS);
    try {
      CourierList courierList = afterShip.getCourierEndpoint().listCouriers();
      System.out.println(courierList);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
