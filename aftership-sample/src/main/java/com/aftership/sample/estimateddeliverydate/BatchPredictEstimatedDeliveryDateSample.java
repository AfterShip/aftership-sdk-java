package com.aftership.sample.estimateddeliverydate;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.estimateddeliverydate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BatchPredictEstimatedDeliveryDateSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    batchPredictEstimatedDeliveryDate(afterShip);
  }

  public static void batchPredictEstimatedDeliveryDate(AfterShip afterShip) {
    System.out.println(EndpointPath.BATCH_PREDICT_ESTIMATED_DELIVERY_DATE);

    Address originAddress = new Address();
    originAddress.setCountry("USA");
    originAddress.setState("WA");
    originAddress.setPostalCode("98108");
    originAddress.setRawLocation("Seattle, Washington, 98108, USA, United States");
    Address destinationAddress = new Address();
    destinationAddress.setCountry("USA");
    destinationAddress.setState("CA");
    destinationAddress.setPostalCode("92019");
    destinationAddress.setRawLocation("El Cajon, California, 92019, USA, United States");

    Weight weight = new Weight();
    weight.setUnit("kg");
    weight.setValue(11);

    OrderProcessingTime orderProcessingTime = new OrderProcessingTime();
    orderProcessingTime.setUnit("day");
    orderProcessingTime.setValue(0);

    EstimatedPickup estimatedPickup = new EstimatedPickup();
    estimatedPickup.setOrderTime("2021-07-01 15:04:05");
    estimatedPickup.setOrderCutoffTime("20:00:00");
    estimatedPickup.setBusinessDays(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7)));
    estimatedPickup.setOrderProcessingTime(orderProcessingTime);


    EstimatedDeliveryDate estimatedDeliveryDate = new EstimatedDeliveryDate();
    estimatedDeliveryDate.setSlug("fedex");
    estimatedDeliveryDate.setServiceTypeName("FEDEX HOME DELIVERY");
    estimatedDeliveryDate.setOriginAddress(originAddress);
    estimatedDeliveryDate.setDestinationAddress(destinationAddress);
    estimatedDeliveryDate.setWeight(weight);
    estimatedDeliveryDate.setPackageCount(1);
    estimatedDeliveryDate.setPickupTime("2021-07-01 15:00:00");

    List<EstimatedDeliveryDate> list = new ArrayList<EstimatedDeliveryDate>() {
    };
    list.add(estimatedDeliveryDate);

    EstimatedDeliveryDateBatchPredictRequest req = new EstimatedDeliveryDateBatchPredictRequest(list);

    try {
      EstimatedDeliveryDateList estimatedDeliveryDateList =
        afterShip.getEstimatedDeliveryDateEndpoint().batchPredictEstimatedDeliveryDate(req.getEstimatedDeliveryDates());

      System.out.println(estimatedDeliveryDateList.getEstimatedDeliveryDates());

    } catch (SdkException | ApiException | RequestException e) {
      throw new RuntimeException(e);
    }
  }
}
