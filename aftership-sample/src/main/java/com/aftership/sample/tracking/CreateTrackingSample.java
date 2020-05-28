package com.aftership.sample.tracking;

import java.util.HashMap;
import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.model.tracking.NewTracking;
import com.aftership.sdk.model.tracking.Tracking;

/** Sample of createTracking method in TrackingEndpoint */
public class CreateTrackingSample {
  public static void main(String[] args) throws ConstructorException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    createTracking(afterShip);
  }

  public static void createTracking(AfterShip afterShip) {
    System.out.println(EndpointPath.CREATE_TRACKING);

    NewTracking newTracking = new NewTracking();
    // slug from listAllCouriers()
    newTracking.setSlug(new String[] {"acommerce"});
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

    try {
      Tracking tracking = afterShip.getTrackingEndpoint().createTracking(newTracking);
      System.out.println(tracking);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
