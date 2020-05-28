package com.aftership.sample.courier;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.model.courier.CourierList;

/** Sample of listAllCouriers method in CourierEndpoint */
public class ListAllCouriersSample {

  public static void main(String[] args) throws ConstructorException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    listAllCouriers(afterShip);
  }

  public static void listAllCouriers(AfterShip afterShip) {
    System.out.println(EndpointPath.LIST_ALL_COURIERS);

    try {
      CourierList courierList = afterShip.getCourierEndpoint().listAllCouriers();
      System.out.println(courierList.getTotal());
      System.out.println(courierList.getCouriers());
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
