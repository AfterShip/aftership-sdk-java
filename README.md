aftership-java
==============

The Java SDK of AfterShip API

Requirements:
	JDK 1.5 or superior.
	org.json .jar, you can download it from: http://www.json.org/
	Junit (only if you want to run the tests).

Quick Start


Get a list of supported couriers

    	ConnectionAPI connection = new ConnectionAPI(“*****-6477-****-93ec-86c4f872fb6b");
   	List<Courier> couriers = connection.getCouriers();	

	//Now we can get information of each element
	couriers.get(0).getSlug();	
	couriers.get(0).getName();
	couriers.get(0).getWeb_url();
	//etc
	
	//if we want to iterate in the list, we can do
	for(int i=0;i<couriers.size();i++)
		couriers.get(i).getSlug();//get slug of each element


Detect which couriers (defined in your account( matches a tracking number
	
	List<Courier> couriers = connection.detectCouriers(“09445246482536”);
	//now in couriers we have the ones that matches

Post a tracking to your account

	//first we have to create a Tracking
	Tracking tracking1 = new Tracking(trackingNumberPost);

	//then we can add information;
        tracking1.setSlug(slugPost);
        tracking1.setTitle(titlePost);
        tracking1.addEmails(email1Post);
        tracking1.addEmails(email2Post);
        tracking1.addSmses(sms1Post);
        tracking1.addSmses(sms2Post);

	//even add customer fields
        tracking1.addCustomFields(“new_field_product_name”,customProductNamePost);
        tracking1.addCustomFields(“new_field_product_price”,customProductPricePost);

	//we add the tracking to our account
        Tracking trackingPosted = connection.postTracking(tracking1);

	//in the response we have exactly the information our account will have about the tracking
	trackingPosted.getTrackingNumber()
	trackingPosted.getSlug()//etc


Delete a tracking of your account

	connection.deleteTracking(trackingNumberDelete,slugDelete)
	//return true if everything is correct, false or exception otherwise

	
Get trackings of your account

	//simplest way, with the page you want to get
	List<Tracking> listTrackings100 = connection.getTracking(1);//get first 100
	List<Tracking> listTrackings200 = connection.getTracking(1);//get 100-200
	//if you delete tracings right before, you may get less number.

	//using Parameters tracking

        ParametersTracking param = new ParametersTracking();
	param.addSlug("dhl");
	//return the number of tracings in your account of dhl
	int totalDHL =connection.getTracking(param);
	//for actually get the trackings
        Tracking aux;
	while(param.hasNext()){
		aux = param.next();
		aux.getTrackingNumber();//here you can iterate on all the result	
	}
	
	//other examples
	//get trackings with destination Spain
	ParametersTracking param1 = new ParametersTracking();
        param1.addDestination(ISO3Country.ESP);
        int totalSpain =connection.getTracking(param1);
	//get trackings that are OutForDelivery
        ParametersTracking param2 = new ParametersTracking();
        param2.addTag(StatusTag.OutForDelivery);
        int totalOutDelivery=connection.getTracking(param2);
	
Get a tracking for your account

        Tracking tracking = connection.getTrackingByNumber("RC328021065CN","canada-post");

Modify a tracking of your account
	
	Tracking tracking = new Tracking("RC328021065CN");
        tracking.setSlug("canada-post");
        tracking.setTitle("another title");

	//returns a tracking with exactly the information of the server
	Tracking tracking2 = connection.putTracking(tracking);
	tracking2.getTitle();//“another title”

Reactivate a tracking of your account

        connection.reactivate("RT224265042HK","hong-kong-post");
	// You can only reactivate an expired tracking
	

Get the last checkpoint of a tracking of your account

        Checkpoint newCheckpoint = connection.getLastCheckpoint("GM605112270084510370", "dhl-global-mail");
	newCheckpoint.getMessage()//"Delivered"
	newCheckpoint.getCountryName()//"BUDERIM QLD, AU"
	newCheckpoint.getTag()//"Delivered"


