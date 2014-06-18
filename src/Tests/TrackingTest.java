package Tests;

import Enums.ISO3Country;
import org.json.JSONObject;
import org.json.JSONArray;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import java.util.Date;

import Classes.*;

public class TrackingTest {

    Tracking tracking;

    @Before
    public void setUp() throws Exception {
        tracking = new Tracking("05167019264110");

    }

    @Test
    public void testAddEmails() throws Exception {
        tracking.addEmails("prueba@gmail.com");
        assertEquals("Emails should have a size of 1", 1, tracking.getEmails().size());
        tracking.addEmails("hola@gmail.com");
        assertEquals("Emails should have a size of 2", 2, tracking.getEmails().size());

    }

    @Test
    public void testDeleteEmail() throws Exception {
        tracking.addEmails("prueba@gmail.com");
        tracking.addEmails("hola@gmail.com");
        assertEquals("Emails should have a size of 2", 2, tracking.getEmails().size());
        tracking.deleteEmail("prueba@gmail.com");
        assertEquals("Emails should have a size of 1", 1, tracking.getEmails().size());
    }

    @Test
    public void testaddSmses() throws Exception {
        tracking.addSmses("+852343434");
        assertEquals("Smses should have a size of 1", 1, tracking.getSmses().size());
        tracking.addSmses("+852343423");
        assertEquals("Smses should have a size of 2", 2, tracking.getSmses().size());
    }

    @Test
    public void testDeleteSmes() throws Exception {
        tracking.addSmses("+852343434");
        tracking.addSmses("+852343423");
        assertEquals("Smses should have a size of 2", 2, tracking.getSmses().size());
        tracking.deleteSmes("+852343423");
        assertEquals("Smses should have a size of 1", 1, tracking.getSmses().size());
    }

    @Test
    public void testGenerateJSON() throws Exception {
//  Element to try
// {
//      "tracking": {
//          "slug": "dhl",
//          "tracking_number": "123456789",
//          "title": "Title Name",
//          "smses": [
//              "+18555072509",
//              "+18555072501"
//           ],
//          "emails": [
//              "email@yourdomain.com",
//              "another_email@yourdomain.com"
//            ],
//          "order_id": "ID 1234",
//          "order_id_path": "http://www.aftership.com/order_id=1234",
//          "custom_fields": {
//              "product_name": "iPhone Case",
//              "product_price": "USD19.99"
//          }
//        }
//}
        Tracking  tracking1 = new Tracking("123456789");
        tracking1.setSlug("dhl");
        tracking1.setTitle("Title Name");
        tracking1.addSmses("+18555072509");
        tracking1.addSmses("+18555072501");
        tracking1.addEmails("email@yourdomain.com");
        tracking1.addEmails("another_email@yourdomain.com");
        tracking1.setOrderID("ID 1234");
        tracking1.setOrderIDPath("http://www.aftership.com/order_id=1234");
        tracking1.addCustomFields("product_name","iPhone Case");
        tracking1.addCustomFields("product_price","USD19.99");

        JSONObject tracking1Json = tracking1.generateJSON();
        //System.out.println(tracking1Json);

        assertEquals("Tracking number should be 123456789 ", "123456789", tracking1Json.getJSONObject("tracking").getString("tracking_number"));
        assertEquals("Slug should be dhl ", "dhl", tracking1Json.getJSONObject("tracking").getString("slug"));
        assertEquals("Title should be Title Name ", "Title Name", tracking1Json.getJSONObject("tracking").getString("title"));

        List<String>  emailsJson = new ArrayList<String>();
        JSONArray emailArray = (JSONArray) tracking1Json.getJSONObject("tracking").get("emails");
        for (int i=0;i<emailArray.length();i++){
            emailsJson.add(emailArray.get(i).toString());
        }
        assertTrue("Emails should contain this email",emailsJson.contains("email@yourdomain.com"));
        assertTrue("Emails should contain this email",emailsJson.contains("another_email@yourdomain.com"));
        assertEquals("Emails should have a size of 2", 2, emailsJson.size());

        List<String>  smsesJson =  new ArrayList<String>();
        JSONArray smsesArray = (JSONArray) tracking1Json.getJSONObject("tracking").get("smses");
        for (int i=0;i<smsesArray.length();i++){
            smsesJson.add(smsesArray.get(i).toString());
        }
        assertTrue("Smses should contain this sms",smsesJson.contains("+18555072509"));
        assertTrue("Smses should contain this sms",smsesJson.contains("+18555072501"));
        assertEquals("Smses should have a size of 2", 2, smsesJson.size());

        assertEquals("OrderID should be ID 1234", "ID 1234", tracking1Json.getJSONObject("tracking").getString("order_id"));
        assertEquals("OrderIDPath should be http://www.aftership.com/order_id=1234", "http://www.aftership.com/order_id=1234",
                tracking1Json.getJSONObject("tracking").getString("order_id_path"));

        assertEquals("Product_name should be this value iPhone Case", "iPhone Case", tracking1Json.getJSONObject("tracking").
                getJSONObject("custom_fields").getString("product_name"));

        assertEquals("Product_price should be this value USD19.99","USD19.99",tracking1Json.getJSONObject("tracking").
                getJSONObject("custom_fields").getString("product_price"));

    }

    @Test
    public void testTracking() throws Exception {

        JSONObject responseJSON;
        responseJSON  = new JSONObject("{\"custom_fields\":{\"product_price\":\"USD19.99\",\"product_name\":\"iPhone Case\"},\"checkpoints\":[],\"signed_by\":null,\"tag\":\"Pending\",\"tracked_count\":0,\"customer_name\":\"Mr Smith\",\"origin_country_iso3\":null,\"emails\":[\"email@yourdomain.com\",\"another_email@yourdomain.com\"],\"order_id\":\"ID 1234\",\"smses\":[\"+85292345678\",\"+85292345679\"],\"title\":\"this title\",\"updated_at\":\"2014-06-12T06:59:27+00:00\",\"source\":\"api\",\"shipment_package_count\":0,\"destination_country_iso3\":\"USA\",\"expected_delivery\":null,\"unique_token\":\"ekHc21knyl\",\"shipment_type\":null,\"created_at\":\"2014-06-12T06:59:27+00:00\",\"tracking_number\":\"05167019264110\",\"active\":true,\"slug\":\"dpd\",\"order_id_path\":\"www.whatever.com\"}\n");

        Tracking newTracking = new Tracking(responseJSON);

        assertEquals("Tracking_number should be 05167019264110", "05167019264110", newTracking.getTrackingNumber());
        assertEquals("Slug should be dpd", "dpd", newTracking.getSlug());

        assertEquals("SignedBy should be null", null, newTracking.getSignedBy());
        assertEquals("Tag should be Pending", "Pending", newTracking.getTag());
        assertEquals("TrackedCount should be 0", 0, newTracking.getTrackedCount());
        assertEquals("CustomerName should be Mr Smith", "Mr Smith", newTracking.getCustomerName());
        assertEquals("OriginCountryISO3 should be null", null, newTracking.getOriginCountryISO3());
        assertEquals("OrderID should be ID 1234", "ID 1234", newTracking.getOrderID());
        assertEquals("OrderIDPath should be www.whatever.com", "www.whatever.com", newTracking.getOrderIDPath());
        assertEquals("Title should be this title", "this title", newTracking.getTitle());
        assertEquals("UpdatedAt should be 2014-06-12T06:59:27+00:00", "2014-06-12T06:59:27+00:00", newTracking.getUpdatedAt());
        assertEquals("DestinationCountryISO3 should be USA", ISO3Country.USA, newTracking.getDestinationCountryISO3());
        assertEquals("ShipmentPackageCount should be 0", 0, newTracking.getShipmentPackageCount());
        assertEquals("Source should be api", "api", newTracking.getSource());
        assertEquals("ExpectedDelivery should be null", null, newTracking.getExpectedDelivery());
        assertEquals("ShipmentType should be null", null, newTracking.getShipmentType());
        //assertEquals("UniqueToken should be ly02uA2okl", "ly02uA2okl", newTracking.uniqueToken());
        assertEquals("Active should be true", true, newTracking.isActive());
        assertEquals("CreatedAt should be 2014-06-12T06:59:27+00:00", "2014-06-12T06:59:27+00:00", newTracking.getCreatedAt());

        List<String> smsesList = newTracking.getSmses();
        assertTrue("Smses should contain this +85292345678",smsesList.contains("+85292345678"));
        assertTrue("Smses should contain this +85292345679",smsesList.contains("+85292345679"));
        assertEquals("Smses should have a size of 2", 2, smsesList.size());

        List<String> emailsList = newTracking.getEmails();
        assertTrue("Emails should contain this email@yourdomain.com",emailsList.contains("email@yourdomain.com"));
        assertTrue("Emails should contain this another_email@yourdomain.com",emailsList.contains("another_email@yourdomain.com"));
        assertEquals("Emails should have a size of 2", 2, emailsList.size());
     }

    @Test
    public void testDates(){

    }

}