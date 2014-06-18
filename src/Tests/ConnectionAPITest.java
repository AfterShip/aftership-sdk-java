package Tests;

import Enums.ISO3Country;
import Enums.StatusTag;
import org.junit.*;

import java.util.*;

import Classes.*;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConnectionAPITest {
    final static int TOTAL_COURIERS_API = 190;
    ConnectionAPI connection = new ConnectionAPI("a61d6204-6477-4f6d-93ec-86c4f872fb6b");
    //getCouriers
    HashMap<String,String> firstCourier= new HashMap<String,String>();
    HashMap<String,String> lastCourier=  new HashMap<String,String>();
    //tracking numbers to detect
    String trackingNumberToDetect ="09445246482536";
    String trackingNumberToDetectError = "asdq";
    String [] couriersDetected={"dpd","fedex"};

    //post tracking number
    String trackingNumberPost ="05167019264110";
    String slugPost = "dpd";
    String orderIDPathPost ="www.whatever.com";
    String orderIDPost ="ID 1234";
    String customerNamePost="Mr Smith";
    String titlePost = "this title";
    ISO3Country countryDestinationPost=ISO3Country.USA;
    String email1Post ="email@yourdomain.com";
    String email2Post ="another_email@yourdomain.com";
    String sms1Post ="+85292345678";
    String sms2Post = "+85292345679";
    String customProductNamePost ="iPhone Case";
    String customProductPricePost ="USD19.99";

    //Tracking to Delete
    String trackingNumberDelete = "596454081704";
    String slugDelete ="fedex";

    //tracking to DeleteBad
    String trackingNumberDelete2 = "798865638020";


    static boolean firstTime = true;

    @Before
    public void setUp()throws Exception{

        //getCouriers

        //first courier
        firstCourier.put("slug","india-post-int");
        firstCourier.put("name","India Post International");
        firstCourier.put("phone","+91 1800 11 2011");
        firstCourier.put("other_name","भारतीय डाक, Speed Post & eMO, EMS, IPS Web");
        firstCourier.put("web_url","http://www.indiapost.gov.in/");

        //last courier
        lastCourier.put("slug","dhl-benelux");
        lastCourier.put("name","DHL Benelux");
        lastCourier.put("phone","+31 26-324 6700");
        lastCourier.put("other_name","DHL Benelux");
        lastCourier.put("web_url","http://www.dhl.nl/");

        if(firstTime) {
            firstTime=false;
            //delete the tracking we are going to post (in case it exist)
            try {
                connection.deleteTracking("05167019264110", "dpd");
                connection.deleteTracking(trackingNumberToDetect, "dpd");
                Tracking newTracking = new Tracking(trackingNumberDelete);
                newTracking.setSlug(slugDelete);
                connection.postTracking(newTracking);

            } catch (Exception e) {
                System.out.println("**"+e.getMessage());
            }
        }


    }

    @Test
    public void testGetCouriers()throws Exception{

        List<Courier> couriers = connection.getCouriers();
        //total Couriers returned
        assertEquals("It should return total couriers", TOTAL_COURIERS_API, couriers.size());
        //check first courier
        Assert.assertEquals("First courier slug", firstCourier.get("slug"), couriers.get(0).getSlug());
        Assert.assertEquals("First courier name", firstCourier.get("name"), couriers.get(0).getName());
        Assert.assertEquals("First courier phone", firstCourier.get("phone"), couriers.get(0).getPhone());
        Assert.assertEquals("First courier other_name", firstCourier.get("other_name"), couriers.get(0).getOther_name());
        Assert.assertEquals("First courier web_url", firstCourier.get("web_url"), couriers.get(0).getWeb_url());
        // Last Courier
       // System.out.println(couriers.get(189));
        Assert.assertEquals("Last courier slug", lastCourier.get("slug"), couriers.get(189).getSlug());
        Assert.assertEquals("Last courier name", lastCourier.get("name"), couriers.get(189).getName());
        Assert.assertEquals("Last courier phone", lastCourier.get("phone"), couriers.get(189).getPhone());
        Assert.assertEquals("Last courier other_name", lastCourier.get("other_name"), couriers.get(189).getOther_name());
        Assert.assertEquals("Last courier web_url", lastCourier.get("web_url"), couriers.get(189).getWeb_url());

        //try to acces with a bad API Key
        ConnectionAPI connectionBadKey = new ConnectionAPI("badKey");

        try{
            connectionBadKey.getCouriers();
        }catch (AftershipAPIException e){
            Assert.assertEquals("Exception should be InvalidCredentials", "InvalidCredentials. Invalid API Key.", e.getMessage());
        }

    }

    @Test
    public void testDetectCouriers() throws Exception {

        //get trackings of this number.
        List<Courier> couriers = connection.detectCouriers(trackingNumberToDetect);
        assertEquals("It should return 2 couriers", 2, couriers.size());
        //the couriers should be dpd or fedex
        assertTrue("It should  have slug equals",couriers.get(0).getSlug().equals(couriersDetected[0])
                || couriers.get(1).getSlug().equals(couriersDetected[0]));
        assertTrue("It should  have slug equals",couriers.get(0).getSlug().equals(couriersDetected[1])
                || couriers.get(1).getSlug().equals(couriersDetected[1]));

        //if the trackingNumber doesn't match any courier defined, should give an error.


        try{
            connection.detectCouriers(trackingNumberToDetectError);
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the tracking number doesn't matching any courier you have defined"
                    , "Invalid JSON data. Cannot detect courier. Activate courier at https://www.aftership.com/settings/courier.", e.getMessage());
        }
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
        tracking1.addCustomFields("product_name",customProductNamePost);
        tracking1.addCustomFields("product_price",customProductPricePost);
        tracking1.addSmses(sms1Post);
        tracking1.addSmses(sms2Post);

        Tracking trackingPosted = connection.postTracking(tracking1);

        Assert.assertEquals("Should be equals TrackingNumber", trackingNumberPost, trackingPosted.getTrackingNumber());
        Assert.assertEquals("Should be equals slug", slugPost, trackingPosted.getSlug());
        Assert.assertEquals("Should be equals orderIDPath", orderIDPathPost, trackingPosted.getOrderIDPath());
        Assert.assertEquals("Should be equals orderID", orderIDPost, trackingPosted.getOrderID());
        Assert.assertEquals("Should be equals countryOrigin", countryDestinationPost,
                trackingPosted.getDestinationCountryISO3());

        Assert.assertTrue("Should contains email",trackingPosted.getEmails().contains(email1Post));
        Assert.assertTrue("Should contains email",trackingPosted.getEmails().contains(email2Post));
        Assert.assertEquals("Should be equals size emails", 2, trackingPosted.getEmails().size());

        Assert.assertTrue("Should contains smses",trackingPosted.getSmses().contains(sms1Post));
        Assert.assertTrue("Should contains smses",trackingPosted.getSmses().contains(sms2Post));
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
         try{
            connection.postTracking(tracking3);
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the tracking number doesn't matching any courier you have defined"
                    , "Invalid JSON data. Cannot detect courier. Activate courier at https://www.aftership.com/settings/courier.", e.getMessage());
        }

    }


    @Test
    public void testDeleteTracking() throws Exception{

         //delete a tracking number (posted in the setup)
         Assert.assertTrue("Delete should return true", connection.deleteTracking(trackingNumberDelete,slugDelete));
         //if the slug is bad informed
         try{
            Assert.assertTrue("Delete should return true", connection.deleteTracking(trackingNumberDelete2,""));
             //always should give an exception before this
             assertTrue("This never should be executed",false);
         }catch (Exception e){
             assertEquals("It should return a exception if the slug is not informed properly"
                     , "ResourceNotFound. The requested resource does not exist.", e.getMessage());
         }
        //if the trackingNumber is bad informed
        try{
            Assert.assertTrue("Delete should return true", connection.deleteTracking("adfa","fedex"));
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the slug is not informed properly"
                    , "ResourceNotFound. Tracking does not exist.", e.getMessage());
        }
    }

    @Test
    public void testGetTracking() throws Exception {


        //get the first 100 Trackings
        List<Tracking> listTrackings100 = connection.getTracking(1);
        Assert.assertEquals("Should receive 100", 100, listTrackings100.size());
        Assert.assertTrue("TrackingNumber should be informed", !listTrackings100.get(0).equals(""));
        Assert.assertTrue("TrackingNumber should be informed",!listTrackings100.get(98).equals(""));

        List<Tracking> listTrackings200= new ArrayList<Tracking>();

        listTrackings200 = connection.getTracking(1);
        Assert.assertEquals("Should receive 100", 100, listTrackings200.size());
        Assert.assertTrue("TrackingNumber should be informed",!listTrackings200.get(0).equals(""));
        Assert.assertTrue("TrackingNumber should be informed",!listTrackings200.get(98).equals(""));
        // System.out.println(listTrackings);
    }

    @Test
    public void testGetTracking2() throws Exception{
        ParametersTracking param = new ParametersTracking();
        param.addSlug("dhl");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH,-1);
        date = c.getTime();
        param.setCreatedAtMin(date);
        int totalDHL =connection.getTracking(param);
        Assert.assertEquals("Should be 35 trackings", 35, totalDHL);

        ParametersTracking param1 = new ParametersTracking();
        param1.addDestination(ISO3Country.ESP);
        int totalSpain =connection.getTracking(param1);
        Assert.assertEquals("Should be 23 trackings", 23, totalSpain);

        ParametersTracking param2 = new ParametersTracking();
        param2.addTag(StatusTag.OutForDelivery);
        int totalOutDelivery=connection.getTracking(param2);
        Assert.assertEquals("Should be 4 trackings", 4, totalOutDelivery);

        Tracking aux;
        while(param2.hasNext()){
            aux = param2.next();
            Assert.assertTrue("TrackingNumber should be",
                    aux.getTrackingNumber().equals("RF228685685SG") ||
                    aux.getTrackingNumber().equals("LK032612415CN") ||
                    aux.getTrackingNumber().equals("RW101815764SG") ||
                    aux.getTrackingNumber().equals("RQ246778993SG"));

        }

    }

    @Test
    public void testGetTrackingByNumber()throws Exception{

        Tracking tracking = connection.getTrackingByNumber("RC328021065CN","canada-post");
        Assert.assertEquals("Should be equals TrackingNumber", "RC328021065CN", tracking.getTrackingNumber());
        Assert.assertEquals("Should be equals Slug", "canada-post", tracking.getSlug());
        Assert.assertEquals("Should be equals type", "Lettermail", tracking.getShipmentType());

        //slug is bad informed
        try{
            connection.getTrackingByNumber("RC328021065CN", "");
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the slug is not informed properly"
                    , "ResourceNotFound. The requested resource does not exist.", e.getMessage());
        }
        //if the trackingNumber is bad informed
        try{
            connection.getTrackingByNumber("adf", "fedex");
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the slug is not informed properly"
                    , "ResourceNotFound. Tracking does not exist.", e.getMessage());
        }

    }

    @Test
    public void testPutTracking()throws Exception{
        Tracking tracking = new Tracking("RC328021065CN");
        tracking.setSlug("canada-post");
        tracking.setTitle("another title");

        Tracking tracking2 = connection.putTracking(tracking);
        Assert.assertEquals("Should be equals title", "another title", tracking2.getTitle());

        //test post tracking number doesn't exist
        Tracking tracking3 = new Tracking(trackingNumberToDetectError);
        tracking3.setTitle("another title");

        try{
            connection.putTracking(tracking3);
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the tracking number doesn't matching any courier you have defined"
                    , "ResourceNotFound. Tracking does not exist.", e.getMessage());
        }
    }

    @Test
    public void testReactivate()throws Exception{

        //Assert.assertTrue("TrackingNumber should be true",connection.reactivate("RB996989481HK","hong-kong-post"));

        //try reactivate one already active
        try{
            connection.reactivate("RT224265042HK","hong-kong-post");
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch(Exception e){
            Assert.assertEquals("Should be equals message",
                    "InvalidArgument. Reactivate is not allowed. You can only reactivate an expired tracking.",
                    e.getMessage());
        }
        //tracking
        try{
            connection.reactivate("RT224265042HK","hong-kong-post");
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch(Exception e){
            Assert.assertEquals("Should be equals message",
                    "InvalidArgument. Reactivate is not allowed. You can only reactivate an expired tracking.",
                    e.getMessage());
        }
        //slug is bad informed
        try{
            connection.reactivate("RT224265042HK","");
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the slug is not informed properly"
                    , "ResourceNotFound. The requested resource does not exist.", e.getMessage());
        }
        //if the trackingNumber is bad informed
        try{
            connection.reactivate("adf", "fedex");
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the slug is not informed properly"
                    , "ResourceNotFound. Tracking does not exist.", e.getMessage());
        }
    }

    @Test
    public void testGetLastCheckpoint()throws Exception{

        Checkpoint newCheckpoint = connection.getLastCheckpoint("GM605112270084510370", "dhl-global-mail");
        Assert.assertEquals("Should be equals message", "Delivered", newCheckpoint.getMessage());
        Assert.assertEquals("Should be equals city name", "BUDERIM QLD, AU", newCheckpoint.getCountryName());
        Assert.assertEquals("Should be equals tag", "Delivered", newCheckpoint.getTag());
        //slug is bad informed
        try{
            connection.getLastCheckpoint("GM605112270084510370", "dhl--mail");
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the slug is not informed properly"
                    , "ResourceNotFound. Tracking does not exist.", e.getMessage());
        }
        //if the trackingNumber is bad informed
        try{
            connection.getLastCheckpoint("ads", "dhl--mail");
            //always should give an exception before this
            assertTrue("This never should be executed",false);
        }catch (Exception e){
            assertEquals("It should return a exception if the slug is not informed properly"
                    , "ResourceNotFound. Tracking does not exist.", e.getMessage());
        }
    }



}