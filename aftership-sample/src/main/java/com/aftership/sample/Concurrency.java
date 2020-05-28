package com.aftership.sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;

/** Examples of concurrent requests */
public class Concurrency {

  public static void main(String[] args) throws InterruptedException, ConstructorException {
    AftershipOption option = new AftershipOption();
    option.setEndpoint("http://localhost:8080/v4");
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), option);

    int nums = 50;
    AtomicInteger count = new AtomicInteger(0);

    int size = 5;
    ExecutorService executor =
        new ThreadPoolExecutor(
            size,
            size,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadPoolExecutor.DiscardOldestPolicy());

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
    try {
      CourierList courierList = afterShip.getCourierEndpoint().listCouriers();
      System.out.println(courierList);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
