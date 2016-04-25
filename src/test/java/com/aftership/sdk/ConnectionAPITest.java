package com.aftership.sdk;

import com.afership.sdk.*;
import com.aftership.sdk.enums.FieldCheckpoint;
import com.aftership.sdk.enums.FieldTracking;
import com.aftership.sdk.enums.ISO3Country;
import com.aftership.sdk.enums.StatusTag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionAPITest {
    final static int TOTAL_COURIERS_API = 196;
    ConnectionAPI connection = new ConnectionAPI("your-api-key-here");

    //getCouriers
    HashMap<String, String> firstCourier = new HashMap<String, String>();
    HashMap<String, String> firstCourierAccount = new HashMap<String, String>();
    //tracking numbers to detect
    String trackingNumberToDetect = "09445246482536";
    String trackingNumberToDetectError = "asdq";
    String[] couriersDetected = {"dpd", "fedex"};

    //post tracking number
    String trackingNumberPost = "05167019264110";
    String slugPost = "dpd";
    String orderIDPathPost = "www.whatever.com";
    String orderIDPost = "ID 1234";
    String customerNamePost = "Mr Smith";
    String titlePost = "this title";
    ISO3Country countryDestinationPost = ISO3Country.USA;
    String email1Post = "email@yourdomain.com";
    String email2Post = "another_email@yourdomain.com";
    String sms1Post = "+85292345678";
    String sms2Post = "+85292345679";
    String customProductNamePost = "iPhone Case";
    String customProductPricePost = "USD19.99";

    //Tracking to Delete
    String trackingNumberDelete = "596454081704";
    String slugDelete = "fedex";

    //tracking to DeleteBad
    String trackingNumberDelete2 = "798865638020";


    static boolean firstTime = true;

    @Before
    public void setUp() throws Exception {

        //first courier
        firstCourier.put("slug", "india-post-int");
        firstCourier.put("name", "India Post International");
        firstCourier.put("phone", "+91 1800 11 2011");
        firstCourier.put("other_name", "भारतीय डाक, Speed Post & eMO, EMS, IPS Web");
        firstCourier.put("web_url", "http://www.indiapost.gov.in/");

        //first courier in your account
        firstCourierAccount.put("slug", "usps");
        firstCourierAccount.put("name", "USPS");
        firstCourierAccount.put("phone", "+1 800-275-8777");
        firstCourierAccount.put("other_name", "United States Postal Service");
        firstCourierAccount.put("web_url", "https://www.usps.com");

        if (firstTime) {
            firstTime = false;
            //delete the tracking we are going to post (in case it exist)
            Tracking tracking = new Tracking("05167019264110");
            tracking.setSlug("dpd");
            try {
                connection.deleteTracking(tracking);
            } catch (Exception e) {
                System.out.println("**1" + e.getMessage());
            }
            Tracking tracking1 = new Tracking(trackingNumberToDetect);
            tracking1.setSlug("dpd");
            try {
                connection.deleteTracking(tracking1);
            } catch (Exception e) {
                System.out.println("**2" + e.getMessage());
            }
            try {
                Tracking newTracking = new Tracking(trackingNumberDelete);
                newTracking.setSlug(slugDelete);
                connection.postTracking(newTracking);
            } catch (Exception e) {
                System.out.println("**3" + e.getMessage());
            }
        }

    }

    @Test
    public void testGetAllCouriers() throws Exception {

        List<Courier> couriers = connection.getAllCouriers();

        //check first courier
        Assert.assertEquals("First courier slug", firstCourier.get("slug"), couriers.get(0).getSlug());
        Assert.assertEquals("First courier name", firstCourier.get("name"), couriers.get(0).getName());
        Assert.assertEquals("First courier phone", firstCourier.get("phone"), couriers.get(0).getPhone());
        Assert.assertEquals("First courier other_name", firstCourier.get("other_name"), couriers.get(0).getOther_name());
        Assert.assertEquals("First courier web_url", firstCourier.get("web_url"), couriers.get(0).getWeb_url());
        //total Couriers returned
        assertEquals("It should return total couriers", TOTAL_COURIERS_API, couriers.size());
        //try to acces with a bad API Key
        ConnectionAPI connectionBadKey = new ConnectionAPI("badKey");

        try {
            connectionBadKey.getCouriers();
        } catch (AftershipAPIException e) {
            Assert.assertEquals("{\"meta\":{\"code\":401,\"message\":\"Invalid API key.\",\"type\":\"Unauthorized\"},\"data\":{}}", e.getMessage());
        }
    }

    @Test
    public void testGetCouriers() throws Exception {

        List<Courier> couriers = connection.getCouriers();
        //total Couriers returned
        assertEquals("It should return total couriers", 8, couriers.size());
        //check first courier
        Assert.assertEquals("First courier slug", firstCourierAccount.get("slug"), couriers.get(0).getSlug());
        Assert.assertEquals("First courier name", firstCourierAccount.get("name"), couriers.get(0).getName());
        Assert.assertEquals("First courier phone", firstCourierAccount.get("phone"), couriers.get(0).getPhone());
        Assert.assertEquals("First courier other_name", firstCourierAccount.get("other_name"), couriers.get(0).getOther_name());
        Assert.assertEquals("First courier web_url", firstCourierAccount.get("web_url"), couriers.get(0).getWeb_url());

        //try to acces with a bad API Key
        ConnectionAPI connectionBadKey = new ConnectionAPI("badKey");

        try {
            connectionBadKey.getCouriers();
        } catch (AftershipAPIException e) {
            Assert.assertEquals("{\"meta\":{\"code\":401,\"message\":\"Invalid API key.\",\"type\":\"Unauthorized\"},\"data\":{}}", e.getMessage());
        }

    }


    @Test
    public void testDetectCouriers() throws Exception {

        //get trackings of this number.
        List<Courier> couriers = connection.detectCouriers(trackingNumberToDetect);
        assertEquals("It should return 2 couriers", 2, couriers.size());
        //the couriers should be dpd or fedex
        assertTrue("It should  have slug equals", couriers.get(0).getSlug().equals(couriersDetected[0])
                || couriers.get(1).getSlug().equals(couriersDetected[0]));
        assertTrue("It should  have slug equals", couriers.get(0).getSlug().equals(couriersDetected[1])
                || couriers.get(1).getSlug().equals(couriersDetected[1]));

        //if the trackingNumber doesn't match any courier defined, should give an error.

        try {
            List<Courier> couriers1 = connection.detectCouriers(trackingNumberToDetectError);
            assertEquals("It should return 0 couriers", 0, couriers1.size());
        } catch (AftershipAPIException e) {
            Assert.assertEquals("{\"meta\":{\"code\":4005,\"message\":\"The value of `tracking_number` is invalid.\",\"type\":\"BadRequest\"},\"data\":{\"tracking\":{\"tracking_number\":\"asdq\"}}}", e.getMessage());
        }

        List<String> slugs = new ArrayList<String>();
        slugs.add("dtdc");
        slugs.add("ukrposhta");
        slugs.add("usps");
        //   slugs.add("asdfasdfasdfasd");
        slugs.add("dpd");
        List<Courier> couriers2 = connection.detectCouriers(trackingNumberToDetect, "28046", "", null, slugs);
        assertEquals("It should return 1 couriers", 1, couriers2.size());
    }


    @Test
    public void testPostTracking() throws Exception {

        //test informed all the fields allowed to post
        Tracking tracking1 = new Tracking(trackingNumberPost);
        tracking1.setSlug(slugPost);
        tracking1.setOrderIDPath(orderIDPathPost);
        tracking1.setCustomerName(customerNamePost);
        tracking1.setOrderID(orderIDPost);
        tracking1.setTitle(titlePost);
        tracking1.setDestinationCountryISO3(countryDestinationPost);
        tracking1.addEmails(email1Post);
        tracking1.addEmails(email2Post);
        tracking1.addCustomFields("product_name", customProductNamePost);
        tracking1.addCustomFields("product_price", customProductPricePost);
        tracking1.addSmses(sms1Post);
        tracking1.addSmses(sms2Post);

        Tracking trackingPosted = connection.postTracking(tracking1);

        Assert.assertEquals("Should be equals TrackingNumber", trackingNumberPost, trackingPosted.getTrackingNumber());
        Assert.assertEquals("Should be equals slug", slugPost, trackingPosted.getSlug());
        Assert.assertEquals("Should be equals orderIDPath", orderIDPathPost, trackingPosted.getOrderIDPath());
        Assert.assertEquals("Should be equals orderID", orderIDPost, trackingPosted.getOrderID());
        Assert.assertEquals("Should be equals countryOrigin", countryDestinationPost,
                trackingPosted.getDestinationCountryISO3());

        Assert.assertTrue("Should contains email", trackingPosted.getEmails().contains(email1Post));
        Assert.assertTrue("Should contains email", trackingPosted.getEmails().contains(email2Post));
        Assert.assertEquals("Should be equals size emails", 2, trackingPosted.getEmails().size());

        Assert.assertTrue("Should contains smses", trackingPosted.getSmses().contains(sms1Post));
        Assert.assertTrue("Should contains smses", trackingPosted.getSmses().contains(sms2Post));
        Assert.assertEquals("Should be equals size smses", 2, trackingPosted.getSmses().size());

        Assert.assertEquals("Should be equals custom field product_name", customProductNamePost,
                trackingPosted.getCustomFields().get("product_name"));
        Assert.assertEquals("Should be equals custom field product_price", customProductPricePost,

                trackingPosted.getCustomFields().get("product_price"));

        //test post only informing trackingNumber (the slug can be dpd and fedex)
        Tracking tracking2 = new Tracking(trackingNumberToDetect);
        Tracking trackingPosted2 = connection.postTracking(tracking2);
        Assert.assertEquals("Should be equals TrackingNumber", trackingNumberToDetect, trackingPosted2.getTrackingNumber());
        Assert.assertEquals("Should be equals slug", "dpd", trackingPosted2.getSlug());//the system assign dpd (it exist)

        //test post tracking number doesn't exist
        Tracking tracking3 = new Tracking(trackingNumberToDetectError);
        try {
            connection.postTracking(tracking3);
            //always should give an exception before this
            assertTrue("This never should be executed", false);
        } catch (Exception e) {
            assertEquals("It should return a exception if the tracking number doesn't matching any courier you have defined"
                    , "{\"meta\":{\"code\":4005,\"message\":\"The value of `tracking_number` is invalid.\",\"type\":\"BadRequest\"},\"data\":{\"tracking\":{\"tracking_number\":\"asdq\",\"title\":\"asdq\"}}}", e.getMessage());
        }

    }


    @Test
    public void testDeleteTracking() throws Exception {

        //delete a tracking number (posted in the setup)
        Tracking deleteTracking = new Tracking(trackingNumberDelete);
        deleteTracking.setSlug(slugDelete);
        Assert.assertTrue("Delete should return true", connection.deleteTracking(deleteTracking));
        //if the slug is bad informed
        try {
            Tracking deleteTracking2 = new Tracking(trackingNumberDelete2);
            Assert.assertTrue("Delete should return true", connection.deleteTracking(deleteTracking2));
            //always should give an exception before this
            assertTrue("This never should be executed", false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("4010"));
            assertTrue(e.getMessage().contains("The value of `slug` is invalid."));
        }
        //if the trackingNumber is bad informed
        try {
            Tracking deleteTracking3 = new Tracking("adfa");
            deleteTracking3.setSlug("fedex");
            Assert.assertTrue("Delete should return true", connection.deleteTracking(deleteTracking3));
            //always should give an exception before this
            assertTrue("This never should be executed", false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("4005"));
            assertTrue(e.getMessage().contains("The value of `tracking_number` is invalid."));

        }
    }

    @Test
    public void testGetTrackings() throws Exception {


        //get the first 100 Trackings
        List<Tracking> listTrackings100 = connection.getTrackings(1);
        // Assert.assertEquals("Should receive 100", 100, listTrackings100.size());
        Assert.assertTrue("TrackingNumber should be informed", !listTrackings100.get(0).equals(""));
        Assert.assertTrue("TrackingNumber should be informed", !listTrackings100.get(80).equals(""));

        List<Tracking> listTrackings200;

        listTrackings200 = connection.getTrackings(2);
        Assert.assertEquals("Should receive 100", 100, listTrackings200.size());
        Assert.assertTrue("TrackingNumber should be informed", !listTrackings200.get(0).equals(""));
        Assert.assertTrue("TrackingNumber should be informed", !listTrackings200.get(80).equals(""));
        // System.out.println(listTrackings);
    }

    @Test
    public void testGetTracking2() throws Exception {
        ParametersTracking param = new ParametersTracking();
        param.addSlug("dhl");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        date = c.getTime();
        param.setCreatedAtMin(date);
        List<Tracking> totalDHL = connection.getTrackings(param);
        Assert.assertEquals("Should be 35 trackings", 2, totalDHL.size());

        ParametersTracking param1 = new ParametersTracking();
        param1.addDestination(ISO3Country.ESP);
        param1.setLimit(20);
        List<Tracking> totalSpain = connection.getTrackings(param1);
        Assert.assertEquals("Should be 20 trackings", 20, totalSpain.size());
        List<Tracking> totalSpain2 = connection.getTrackingsNext(param1);
        Assert.assertEquals("Should be 3 trackings", 3, totalSpain2.size());


        ParametersTracking param2 = new ParametersTracking();
        param2.addTag(StatusTag.Pending);
        List<Tracking> totalOutDelivery = connection.getTrackings(param2);
//        Assert.assertEquals("Should be 2 trackings", 1, totalOutDelivery.size());

        ParametersTracking param3 = new ParametersTracking();
        param3.setLimit(195);
        List<Tracking> totalOutDelivery1 = connection.getTrackings(param3);
        Assert.assertEquals("Should be 195 trackings", 195, totalOutDelivery1.size());

        ParametersTracking param4 = new ParametersTracking();
        param4.setKeyword("title");
        param4.addField(FieldTracking.title);
        List<Tracking> totalOutDelivery2 = connection.getTrackings(param4);
        Assert.assertEquals("Should be 1 trackings", 2, totalOutDelivery2.size());
        Assert.assertEquals("Should be equal", "this title", totalOutDelivery2.get(0).getTitle());


        ParametersTracking param5 = new ParametersTracking();
        param5.addField(FieldTracking.tracking_number);
        List<Tracking> totalOutDelivery3 = connection.getTrackings(param5);
        Assert.assertEquals("Should be null", null, totalOutDelivery3.get(0).getTitle());

        ParametersTracking param6 = new ParametersTracking();
        param6.addField(FieldTracking.tracking_number);
        param6.addField(FieldTracking.title);
        param6.addField(FieldTracking.checkpoints);
        param6.addField(FieldTracking.order_id);
        param6.addField(FieldTracking.tag);
        param6.addField(FieldTracking.order_id);
        List<Tracking> totalOutDelivery4 = connection.getTrackings(param6);
        Assert.assertEquals("Should be null", null, totalOutDelivery4.get(0).getSlug());

        ParametersTracking param7 = new ParametersTracking();
        param7.addOrigin(ISO3Country.ESP);
        List<Tracking> totalOutDelivery5 = connection.getTrackings(param7);
        Assert.assertEquals("Should be equal", 8, totalOutDelivery5.size());

//        ParametersTracking param3 = new ParametersTracking();
//        int totalTotal = connection.getTracking(param3);
//        System.out.println(totalTotal);
//        int number=0;
//        while(param3.hasNext()){
//            System.out.println(++number +". "+param3.next().getTrackingNumber());
//        }


    }

    @Test
    public void testGetTrackingByNumber() throws Exception {
        Tracking trackingGet1 = new Tracking("RC328021065CN");
        trackingGet1.setSlug("canada-post");

        Tracking tracking = connection.getTrackingByNumber(trackingGet1);
        Assert.assertEquals("Should be equals TrackingNumber", "RC328021065CN", tracking.getTrackingNumber());
        Assert.assertEquals("Should be equals Slug", "canada-post", tracking.getSlug());
        Assert.assertEquals("Should be equals type", "Lettermail", tracking.getShipmentType());

        //slug is bad informed
        try {
            Tracking trackingGet2 = new Tracking("RC328021065CN");

            connection.getTrackingByNumber(trackingGet2);
            //always should give an exception before this
            assertTrue("This never should be executed", false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("4010"));
            assertTrue(e.getMessage().contains("The value of `slug` is invalid."));
        }

        //if the trackingNumber is bad informed
        try {
            Tracking trackingGet3 = new Tracking("adf");
            trackingGet1.setSlug("fedex");
            connection.getTrackingByNumber(trackingGet3);
            //always should give an exception before this
            assertTrue("This never should be executed", false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("4005"));
            assertTrue(e.getMessage().contains("The value of `tracking_number` is invalid."));
        }

    }

    @Test
    public void testGetTrackingByNumber2() throws Exception {
        List<FieldTracking> fields = new ArrayList<FieldTracking>();
        fields.add(FieldTracking.tracking_number);
        Tracking trackingGet1 = new Tracking("RC328021065CN");
        trackingGet1.setSlug("canada-post");
        Tracking tracking3 = connection.getTrackingByNumber(trackingGet1, fields, "");
        Assert.assertEquals("Should be equals TrackingNumber", "RC328021065CN", tracking3.getTrackingNumber());
        Assert.assertEquals("Should be equals title", null, tracking3.getTitle());
        Assert.assertEquals("Should be equals slug", null, tracking3.getSlug());
        Assert.assertEquals("Should be equals checkpoint", null, tracking3.getCheckpoints());
    }

    @Test
    public void testPutTracking() throws Exception {
        Tracking tracking = new Tracking("RC328021065CN");
        tracking.setSlug("canada-post");
        tracking.setTitle("another title");

        Tracking tracking2 = connection.putTracking(tracking);
        Assert.assertEquals("Should be equals title", "another title", tracking2.getTitle());

        //test post tracking number doesn't exist
        Tracking tracking3 = new Tracking(trackingNumberToDetectError);
        tracking3.setTitle("another title");

        try {
            connection.putTracking(tracking3);
            //always should give an exception before this
            assertTrue("This never should be executed", false);
        } catch (Exception e) {
            assertEquals("It should return a exception if the tracking number doesn't matching any courier you have defined"
                    , "{\"meta\":{\"code\":4005,\"message\":\"The value of `tracking_number` is invalid.\",\"type\":\"BadRequest\"},\"data\":{\"tracking\":{\"title\":\"another title\"}}}", e.getMessage());
        }
    }


    @Test
    public void testGetLastCheckpoint() throws Exception {
        Tracking trackingGet1 = new Tracking("GM605112270084510370");
        trackingGet1.setSlug("dhl-global-mail");
        Checkpoint newCheckpoint = connection.getLastCheckpoint(trackingGet1);
        Assert.assertEquals("Should be equals message", "Delivered", newCheckpoint.getMessage());
        Assert.assertEquals("Should be equals city name", "BUDERIM QLD, AU", newCheckpoint.getCountryName());
        Assert.assertEquals("Should be equals tag", "Delivered", newCheckpoint.getTag());
        //slug is bad informed
        try {
            Tracking trackingGet2 = new Tracking("GM605112270084510370");
            trackingGet2.setSlug("dhl--mail");
            connection.getLastCheckpoint(trackingGet2);
            //always should give an exception before this
            assertTrue("This never should be executed", false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("4010"));
            assertTrue(e.getMessage().contains("The value of `slug` is invalid."));
        }
        //if the trackingNumber is bad informed
        try {
            Tracking trackingGet2 = new Tracking("ads");
            trackingGet2.setSlug("dhl--mail");
            connection.getLastCheckpoint(trackingGet2);
            //always should give an exception before this
            assertTrue("This never should be executed", false);
        } catch (Exception e) {
            assertEquals("It should return a exception if the slug is not informed properly"
                    , "{\"meta\":{\"code\":4005,\"message\":\"The value of `tracking_number` is invalid.\",\"type\":\"BadRequest\"},\"data\":{\"tracking\":{\"tracking_number\":\"ads\",\"slug\":\"dhl--mail\"}}}", e.getMessage());
        }
    }

    @Test
    public void testGetLastCheckpoint2() throws Exception {
        List<FieldCheckpoint> fields = new ArrayList<FieldCheckpoint>();
        fields.add(FieldCheckpoint.message);
        Tracking trackingGet1 = new Tracking("GM605112270084510370");
        trackingGet1.setSlug("dhl-global-mail");

        Checkpoint newCheckpoint1 = connection.getLastCheckpoint(trackingGet1, fields, "");
        assertEquals("Should be equals message", "Delivered", newCheckpoint1.getMessage());
        assertEquals("Should be equals", null, newCheckpoint1.getCreatedAt());

        fields.add(FieldCheckpoint.created_at);
        System.out.println("list:" + fields.toString());
        Checkpoint newCheckpoint2 = connection.getLastCheckpoint(trackingGet1, fields, "");
        assertEquals("Should be equals message", "Delivered", newCheckpoint2.getMessage());
        assertEquals("Should be equals", "2014-06-17T04:19:38+00:00", newCheckpoint2.getCreatedAt());
    }

    @Test
    public void testGetLastCheckpoint3() throws Exception {
        List<FieldCheckpoint> fields = new ArrayList<FieldCheckpoint>();
        fields.add(FieldCheckpoint.message);
        Tracking trackingGet1 = new Tracking("8W9JM0014847A094");
        trackingGet1.setSlug("arrowxl");
        trackingGet1.setTrackingPostalCode("BB102PN");

        Checkpoint newCheckpoint1 = connection.getLastCheckpoint(trackingGet1, fields, "");
        assertEquals("Should be equals message", "Delivered", newCheckpoint1.getMessage());

    }

    @Test
    public void testRetrack() throws Exception {

        Tracking tracking = new Tracking("RT224265042HK");
        tracking.setSlug("hong-kong-post");
        try {
            connection.retrack(tracking);
            assertTrue("This never should be executed", false);

        } catch (Exception e) {
            assertTrue(e.getMessage().contains("4016"));
            assertTrue(e.getMessage().contains("Retrack is not allowed. You can only retrack each shipment once."));

        }
    }

///Test by ID


    @Test
    public void testDeleteTrackingID() throws Exception {
        Tracking newTracking = new Tracking("RT406182863DE");
        newTracking.setSlug("deutsch-post");
        newTracking.setTrackingShipDate("20140627");

        connection.deleteTracking(newTracking);

        Tracking trackingPosted = connection.postTracking(newTracking);

        Tracking whatever = new Tracking("whatever");
        whatever.setId(trackingPosted.getId());


        Assert.assertTrue("Delete should return true", connection.deleteTracking(whatever));

        connection.postTracking(newTracking);
        //if the slug is bad informed

    }

    @Test
    public void testGetTrackingByNumberID() throws Exception {
        Tracking trackingGet1 = new Tracking("whatever");
        trackingGet1.setId("539fc1d68a6157923f0a9284");

        Tracking tracking = connection.getTrackingByNumber(trackingGet1);
        Assert.assertEquals("Should be equals TrackingNumber", "RC328021065CN", tracking.getTrackingNumber());
        Assert.assertEquals("Should be equals Slug", "canada-post", tracking.getSlug());
        Assert.assertEquals("Should be equals type", "Lettermail", tracking.getShipmentType());

    }

    @Test
    public void testGetTrackingByNumber2ID() throws Exception {
        List<FieldTracking> fields = new ArrayList<FieldTracking>();
        fields.add(FieldTracking.tracking_number);
        Tracking trackingGet1 = new Tracking("whatever");
        trackingGet1.setId("539fc1d68a6157923f0a9284");
        Tracking tracking3 = connection.getTrackingByNumber(trackingGet1, fields, "");
        Assert.assertEquals("Should be equals TrackingNumber", "RC328021065CN", tracking3.getTrackingNumber());
        Assert.assertEquals("Should be equals title", null, tracking3.getTitle());
        Assert.assertEquals("Should be equals slug", null, tracking3.getSlug());
        Assert.assertEquals("Should be equals checkpoint", null, tracking3.getCheckpoints());
    }

    @Test
    public void testPutTrackingID() throws Exception {
        Tracking tracking = new Tracking("whatever");
        tracking.setId("539fc1d68a6157923f0a9284");

        tracking.setTitle("another title");

        Tracking tracking2 = connection.putTracking(tracking);
        Assert.assertEquals("Should be equals title", "another title", tracking2.getTitle());

        //test post tracking number doesn't exist
        Tracking tracking3 = new Tracking(trackingNumberToDetectError);
        tracking3.setId("111111111111111");

        try {
            connection.putTracking(tracking3);
            //always should give an exception before this
            assertTrue("This never should be executed", false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("4015"));
            assertTrue(e.getMessage().contains("The value of `id` is invalid."));
        }
    }


    @Test
    public void testGetLastCheckpointID() throws Exception {
        Tracking trackingGet1 = new Tracking("whatever");
        trackingGet1.setId("539fc1d9f9b60c804a0a0f74");
        Checkpoint newCheckpoint = connection.getLastCheckpoint(trackingGet1);
        Assert.assertEquals("Should be equals message", "Delivered", newCheckpoint.getMessage());
        Assert.assertEquals("Should be equals city name", "BUDERIM QLD, AU", newCheckpoint.getCountryName());
        Assert.assertEquals("Should be equals tag", "Delivered", newCheckpoint.getTag());
    }

    @Test
    public void testGetLastCheckpoint2ID() throws Exception {
        List<FieldCheckpoint> fields = new ArrayList<FieldCheckpoint>();
        fields.add(FieldCheckpoint.message);
        Tracking trackingGet1 = new Tracking("whatever");
        trackingGet1.setId("539fc1d9f9b60c804a0a0f74");

        Checkpoint newCheckpoint1 = connection.getLastCheckpoint(trackingGet1, fields, "");
        assertEquals("Should be equals message", "Delivered", newCheckpoint1.getMessage());
        assertEquals("Should be equals", null, newCheckpoint1.getCreatedAt());

        fields.add(FieldCheckpoint.created_at);
        System.out.println("list:" + fields.toString());
        Checkpoint newCheckpoint2 = connection.getLastCheckpoint(trackingGet1, fields, "");
        assertEquals("Should be equals message", "Delivered", newCheckpoint2.getMessage());
        assertEquals("Should be equals", "2014-06-17T04:19:38+00:00", newCheckpoint2.getCreatedAt());
    }

    @Test
    public void testGetLastCheckpoint3ID() throws Exception {
        List<FieldCheckpoint> fields = new ArrayList<FieldCheckpoint>();
        fields.add(FieldCheckpoint.message);
        Tracking trackingGet1 = new Tracking("whatever");
        trackingGet1.setId("53bb4db6dcebe7242fe3283e");
//        trackingGet1.setSlug("arrowxl");
//        trackingGet1.setTrackingPostalCode("BB102PN");

        Checkpoint newCheckpoint1 = connection.getLastCheckpoint(trackingGet1, fields, "");
        assertEquals("Should be equals message", "Delivered", newCheckpoint1.getMessage());

    }
}
