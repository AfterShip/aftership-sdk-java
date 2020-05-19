package com.aftership.sample.tracking;

import java.util.HashMap;
import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.tracking.CreateTrackingRequest;
import com.aftership.sdk.model.tracking.NewTracking;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.rest.DataEntity;

/** Sample of createTracking method in TrackingEndpoint */
public class CreateTrackingSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    createTracking(afterShip);
  }

  public static void createTracking(AfterShip afterShip) {
    System.out.println(EndpointPath.CREATE_TRACKING);

    NewTracking newTracking = new NewTracking();
    newTracking.setSlug(new String[] {"mx-cargo"});
    newTracking.setTrackingNumber("1234567890");
    newTracking.setTitle("Title Name");
    newTracking.setSmses(new String[] {"+18555072509", "+18555072501"});
    newTracking.setEmails(new String[] {"email@yourdomain.com", "another_email@yourdomain.com"});
    newTracking.setOrderId("ID 1234");
    newTracking.setOrderIdPath("http://www.aftership.com/order_id=1234");
    newTracking.setCustomFields(
        new HashMap<String, String>(2) {
          {
            put("product_name", "iPhone Case");
            put("product_price", "USD19.99");
          }
        });
    newTracking.setLanguage("en");
    newTracking.setOrderPromisedDeliveryDate("2019-05-20");
    newTracking.setDeliveryType("pickup_at_store");
    newTracking.setPickupLocation("Flagship Store");
    newTracking.setPickupNote(
        "Reach out to our staffs when you arrive our stores for shipment pickup");
    CreateTrackingRequest request = new CreateTrackingRequest(newTracking);

    DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().createTracking(request);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
      System.out.println(entity.getError().getCode());
      System.out.println(entity.getError().getMessage());
    } else {
      System.out.println(entity.getData().getTracking());
    }
  }
}
