package test.Test;

import Enums.Field;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import org.json.*;
import Classes.*;


import static org.junit.Assert.assertEquals;

public class ConnectionAPITest {
    ConnectionAPI connection  = new ConnectionAPI("a61d6204-6477-4f6d-93ec-86c4f872fb6b");
    private static boolean setUpIsDone = false;

    @Ignore
    public void setUp()throws Exception{

        if (setUpIsDone) {
            return;
        }
//        Tracking  tracking1 = new Tracking("RC933607107HK");
//        tracking1.setSlug("hong-kong-post");
//        JSONObject response = connection.postTracking(tracking1);
//        System.out.println(response);
//
//        Tracking  tracking2= new Tracking("RT224265042HK");
//        tracking2.setSlug("hong-kong-post");
//        response = connection.postTracking(tracking2);
//        System.out.println(response);
//
//        Tracking  tracking3 = new Tracking("LK059460815HK");
//        tracking3.setSlug("hong-kong-post");
//        response = connection.postTracking(tracking3);
//        System.out.println(response);
//
//        Tracking  tracking4 = new Tracking("9400109699939938223564");
//        tracking4.setSlug("usps");
//        response = connection.postTracking(tracking4);
//        System.out.println(response);
//
//        Tracking  tracking5 = new Tracking("9374889949033131111143");
//        tracking5.setSlug("usps");
//        response = connection.postTracking(tracking5);
//        System.out.println(response);
//
//        Tracking  tracking6 = new Tracking("9405509699939943080223");
//        tracking6.setSlug("usps");
//        response = connection.postTracking(tracking6);
//        System.out.println(response);

        setUpIsDone = true;
    }

    @Ignore
    public void testGetCouriers() throws Exception {


        List<Courier> couriers = connection.getCouriers();
        //total Couriers returned
        assertEquals("It should return 190 couriers", 190, couriers.size());
        /* First Courier
        {"slug":"india-post-int","name":"India Post International","phone":"+91 1800 11 2011",
        "other_name":"भारतीय डाक, Speed Post & eMO, EMS, IPS Web","web_url":"http://www.indiapost.gov.in/"}
        */
        Assert.assertEquals("First courier slug should be india-post-int", "india-post-int", couriers.get(0).getSlug());
        Assert.assertEquals("First courier name should be India Post International", "India Post International", couriers.get(0).getName());
        Assert.assertEquals("First courier phone should be +91 1800 11 2011", "+91 1800 11 2011", couriers.get(0).getPhone());
        Assert.assertEquals("First courier other_name should be भारतीय डाक, Speed Post & eMO, EMS, IPS Web", "भारतीय डाक, Speed Post & eMO, EMS, IPS Web", couriers.get(0).getOther_name());
        Assert.assertEquals("First courier web_url be http://www.indiapost.gov.in/", "http://www.indiapost.gov.in/", couriers.get(0).getWeb_url());
        /* Last Courier
        {"slug":"la-poste-colissimo","name":"La Poste Colissimo","phone":"+33 3631","other_name":"Coliposte",
        "web_url":"http://www.csuivi.courrier.laposte.fr"}
        */
        Assert.assertEquals("Last courier slug should be sto", "sto", couriers.get(189).getSlug());
        Assert.assertEquals("Last courier name should be sto express", "sto express", couriers.get(189).getName());
        Assert.assertEquals("Last courier phone should be +86 95543", "+86 95543", couriers.get(189).getPhone());
        Assert.assertEquals("Last courier other_name should be 申通快遞, 申通快递", "申通快遞, 申通快递", couriers.get(189).getOther_name());
        Assert.assertEquals("Last courier web_url shouldbe http://www.sto.cn/", "http://www.sto.cn/", couriers.get(189).getWeb_url());

        ConnectionAPI connectionBadKey = new ConnectionAPI("badKey");

        try{
            connectionBadKey.getCouriers();
        }catch (AftershipAPIException e){
            Assert.assertEquals("Exception should be InvalidCredentials", "InvalidCredentials - Invalid API Key.", e.getMessage());
        }

    }
    @Test
    public void testDetectCouriers() throws Exception {
        List<Courier> couriers = connection.detectCouriers("05167019264110");
        assertEquals("It should return 2 couriers", 2, couriers.size());
       // System.out.println(couriers);
        try{
            connection.detectCouriers("asd1");

        }catch (Exception e){
            assertEquals("It should return a exception if the tracking number doesn't matching any courier you have defined"
                    , "Invalid JSON data.", e.getMessage());
        }
    }

    @Ignore
    public void testPostTracking() throws Exception {

        Tracking tracking1 = new Tracking("05167019264110");
        tracking1.setSlug("dpd");
        tracking1.setOrderIDPath("www.whatever.com");
        tracking1.setCustomerName("Mr Smith");
        tracking1.setOrderID("ID 1234");
        tracking1.setTitle("this title");
        tracking1.setDestinationCountryISO3("USA");
        tracking1.addEmails("email@yourdomain.com");
        tracking1.addEmails("another_email@yourdomain.com");
        tracking1.addCustomFields("product_name","iPhone Case");
        tracking1.addCustomFields("product_price","USD19.99");
        tracking1.addSmses("+85292345678");
        tracking1.addSmses("+85292345679");

        Tracking trackingPosted = connection.postTracking(tracking1);
        JSONObject objectJSON= connection.deleteTracking("dpd","05167019264110");
        System.out.println(trackingPosted);

    }

    @Test
    public void testGetTracking() throws Exception {

        List<Tracking> listTrackings = new ArrayList<Tracking>();
        listTrackings = connection.getTracking(1);
       // System.out.println(listTrackings);

    }

    @Test
    public void testGetTracking2() throws Exception{
        ParametersTracking param = new ParametersTracking();
//        param.addSlug("hong-kong-post");
        param.addField(Field.message);
        param.addField(Field.title);
        param.addField(Field.checkpoints);
        param.addField(Field.checkpointTime);
        param.setLimit(1);
        int results = connection.getTracking(param);
//        for (Tracking tracking: param){
//            System.out.println("imprimiendo");
//            System.out.println(tracking);
//        }
//        System.out.println(param.getBuffer());
//        assertEquals("It should return 3, the HKpost in the account", 3, results);




    }
    @Test
    public void testGetTrackingByNumber()throws Exception{

        Tracking tracking = connection.getTrackingByNumber("9405509699939943080223","usps");
      //  System.out.println(tracking);


    }

    @Test
    public void testPutTracking()throws Exception{
        Tracking tracking2 = new Tracking("9405509699939943080223");
        tracking2.setTitle("pedo");
        tracking2.setSlug("usps");
        System.out.println(connection.putTracking(tracking2));

//        System.out.println(tracking)

    }

    @Test
    public void testReactivate()throws Exception{
        connection.reactivate("RT224265042HK","hong-kong-post");
    }

    @Test
    public void testGetLastCheckpoint()throws Exception{

        System.out.println(connection.getLastCheckpoint("9405509699939943080223", "usps"));

    }


}