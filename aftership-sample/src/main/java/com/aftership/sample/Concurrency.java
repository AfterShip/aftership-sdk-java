package com.aftership.sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.DataEntity;

public class Concurrency {

  public static void main(String[] args) throws InterruptedException {
    AftershipOption option = new AftershipOption();
    option.setEndpoint("http://localhost:8080/v4");
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), option);

    int nums = 50;
    AtomicInteger count = new AtomicInteger(0);
    ExecutorService executor = Executors.newFixedThreadPool(10);
    for (int i = 0; i < nums; i++) {
      int cur = i;
      executor.execute(
          () -> {
            try {
              long begin = System.currentTimeMillis();
              listCouriers(afterShip);
              System.out.println(
                  String.format("time%s: %s ms", cur, System.currentTimeMillis() - begin));
              count.addAndGet(1);
            } catch (Exception e) {
              e.printStackTrace();
            }
          });
    }

    while (true) {
      if (count.get() >= nums) {
        break;
      } else {
        Thread.sleep(500);
      }
    }

    executor.shutdown();
    System.out.println(">> complete count: " + count.get());
  }

  private static void listCouriers(AfterShip afterShip) {
    System.out.println(EndpointPath.LIST_COURIERS);
    DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listCouriers();
    if (entity.hasError()) {
      System.out.println(entity.getError().getCode());
    } else {
      System.out.println(entity.getData().getTotal());
    }
  }
}
