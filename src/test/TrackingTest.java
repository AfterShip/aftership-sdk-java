package test;

import org.json.JSONObject;
import org.json.JSONArray;

import org.junit.Before;
import org.junit.Test;
import code.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class TrackingTest {

    Tracking tracking;

    @Before
    public void setUp() throws Exception {
        tracking = new Tracking("05167019264110");

    }

    @Test
    public void testAddEmails() throws Exception {
        tracking.addEmails("prueba@gmail.com");
        assertEquals("It should have a size of 1", 1, tracking.getEmails().size());
        tracking.addEmails("hola@gmail.com");
        assertEquals("It should have a size of 2", 2, tracking.getEmails().size());

    }

    @Test
    public void testDeleteEmail() throws Exception {
        tracking.addEmails("prueba@gmail.com");
        tracking.addEmails("hola@gmail.com");
        assertEquals("It should have a size of 2", 2, tracking.getEmails().size());
        tracking.deleteEmail("prueba@gmail.com");
        assertEquals("It should have a size of 1", 1, tracking.getEmails().size());
    }

    @Test
    public void testAddSmes() throws Exception {
        tracking.addSmes("+852343434");
        assertEquals("It should have a size of 1", 1, tracking.getSmes().size());
        tracking.addSmes("+852343423");
        assertEquals("It should have a size of 2", 2, tracking.getSmes().size());
    }

    @Test
    public void testDeleteSmes() throws Exception {
        tracking.addSmes("+852343434");
        tracking.addSmes("+852343423");
        assertEquals("It should have a size of 2", 2, tracking.getSmes().size());
        tracking.deleteSmes("+852343423");
        assertEquals("It should have a size of 1", 1, tracking.getSmes().size());
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
        tracking1.addSmes("+18555072509");
        tracking1.addSmes("+18555072501");
        tracking1.addEmails("email@yourdomain.com");
        tracking1.addEmails("another_email@yourdomain.com");
        tracking1.setOrderID("ID 1234");
        tracking1.setOrderIDPath("http://www.aftership.com/order_id=1234");
        tracking1.addCustomFields("product_name","iPhone Case");
        tracking1.addCustomFields("product_price","USD19.99");

        JSONObject tracking1Json = tracking1.generateJSON();
        //System.out.println(tracking1Json);

        assertEquals("It should be 123456789 ", "123456789", tracking1Json.getJSONObject("tracking").getString("tracking_number"));
        assertEquals("It should be dhl ", "dhl", tracking1Json.getJSONObject("tracking").getString("slug"));
        assertEquals("It should be Title Name ", "Title Name", tracking1Json.getJSONObject("tracking").getString("title"));

        List<String>  emailsJson = new ArrayList<String>();
        JSONArray emailArray = (JSONArray) tracking1Json.getJSONObject("tracking").get("emails");
        for (int i=0;i<emailArray.length();i++){
            emailsJson.add(emailArray.get(i).toString());
        }
        assertTrue("It should contain this email",emailsJson.contains("email@yourdomain.com"));
        assertTrue("It should contain this email",emailsJson.contains("another_email@yourdomain.com"));
        assertEquals("It should have a size of 2", 2, emailsJson.size());

        List<String>  smsesJson =  new ArrayList<String>();
        JSONArray smsesArray = (JSONArray) tracking1Json.getJSONObject("tracking").get("smses");
        for (int i=0;i<smsesArray.length();i++){
            smsesJson.add(smsesArray.get(i).toString());
        }
        assertTrue("It should contain this sms",smsesJson.contains("+18555072509"));
        assertTrue("It should contain this sms",smsesJson.contains("+18555072501"));
        assertEquals("It should have a size of 2", 2, smsesJson.size());

        assertEquals("It should be ID 1234", "ID 1234", tracking1Json.getJSONObject("tracking").getString("order_id"));
        assertEquals("It should be http://www.aftership.com/order_id=1234", "http://www.aftership.com/order_id=1234",
                tracking1Json.getJSONObject("tracking").getString("order_id_path"));

        assertEquals("It should be this value iPhone Case", "iPhone Case", tracking1Json.getJSONObject("tracking").
                getJSONObject("custom_fields").getString("product_name"));

        assertEquals("It should be this value USD19.99","USD19.99",tracking1Json.getJSONObject("tracking").
                getJSONObject("custom_fields").getString("product_price"));

    }
}