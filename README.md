aftership-java
==============

The Java SDK of AfterShip API

Requirements:

- JDK 1.5 or superior.
- org.json .jar, you can download it from: http://www.json.org/
- Junit (only if you want to run the tests).

Quick Start
--------------


**Get a list of supported couriers**

	//Create a connectionAPI with your API Key (you will link it to your account)
  	ConnectionAPI connection = new ConnectionAPI(“?????-6477-?????-93ec-86c4f872fb6b");

   	List<Courier> couriers = connection.getCouriers();

	//Now we can get information of each element
	couriers.get(0).getSlug();
	couriers.get(0).getName();
	couriers.get(0).getWeb_url();
	//etc

	//If we want to iterate in the list, we can do
	for(int i=0;i<couriers.size();i++)
		couriers.get(i).getSlug();//Get slug of each element


**Detect which couriers defined in your account match a tracking number**

	List<Courier> couriers = connection.detectCouriers(“09445246482536”);
	//Now in couriers we have the Couriers that match


**Post a tracking to your account**

	//First we have to create a Tracking
	Tracking tracking1 = new Tracking("05167019264110");

	//Then we can add information;
    tracking1.setSlug("dpd");
    tracking1.setTitle("this title");
    tracking1.addEmails("email@yourdomain.com");
    tracking1.addEmails("another_email@yourdomain.com");
    tracking1.addSmses("+85292345678");
    tracking1.addSmses("+85292345679");

	//Even add customer fields
    tracking1.addCustomFields(“product_name”,"iPhone Case");
    tracking1.addCustomFields(“product_price”,"USD19.99");

	//Finally we add the tracking to our account
    Tracking trackingPosted = connection.postTracking(tracking1);

	//In the response we will have exactly the information of the server
	trackingPosted.getTrackingNumber();
	trackingPosted.getSlug();
	//Etc.


**Delete a tracking of your account**

	//Return true if everything is correct, false or exception otherwise
	
	Tracking trackingDelete = new Tracking("123456789");//tracking number
	trackingDelete.setSlug("dhl");
	connection.deleteTracking(trackingDelete);


**Get trackings of your account, there is two ways**

	//1- Simplest way, with the page you want to get

	List<Tracking> listTrackings100 = connection.getTrackings(1);//get first 100
	List<Tracking> listTrackings200 = connection.getTrackings(2);//get 100-200
	//If you delete tracings right before, you may get less number.

	//2- Using Parameters tracking

	//Create a new Parameter
	ParametersTracking param = new ParametersTracking();

	//Add the information we want in the parameter
	param.addSlug("dhl");//Add slug to our parameters
	Date date = new Date();//Create a date with value of now
	Calendar c = Calendar.getInstance();
	c.setTime(date);
    c.add(Calendar.MONTH,-1); //Substract a Month to the date
    date = c.getTime();
    param.setCreatedAtMin(date);//SetCreadtedMin to the date of one month ago

	//Return the first page of trackings in your account from dhl and created less than a month ago.
	List <Tracking> totalDHL =connection.getTrackings(param);


	//if the get has several pages, you can either modify the page you want in your get with param.setPage(page), or
	call getTrackingsNext(param1) instead, it automatically increase the page , example:

	//Get trackings with destination Spain, total 23
	ParametersTracking param1 = new ParametersTracking();
    param1.addDestination(ISO3Country.ESP);
    param1.setLimit(20);//set limit of the page to 20
    List<Tracking> totalSpain =connection.getTrackings(param1);//we will receive the 20 first
    List<Tracking> totalSpain2 =connection.getTrackingsNext(param1); //we will receive the next 3
    int total = param1.getTotal(); // we will receive the total 23;

	//Get trackings that are OutForDelivery
    ParametersTracking param2 = new ParametersTracking();
    param2.addTag(StatusTag.OutForDelivery);
    int totalOutDelivery=connection.getTrackings(param2);


**Get a tracking from your account**

	Tracking trackingToGet = new Tracking("RC328021065CN");
	trackingToGet.setSlug("canada-post");

	Tracking tracking = connection.getTrackingByNumber(trackingToGet);


**Modify a tracking from your account**

	//Create a tracking
	Tracking tracking = new Tracking("RC328021065CN");
    tracking.setSlug("canada-post");
    //Add the fields we want to modify
    tracking.setTitle("another title");

	//Returns a tracking with exactly the information of the server
	Tracking tracking2 = connection.putTracking(tracking);
	tracking2.getTitle();//Value “another title”


**Retrack a tracking of your account**

	Tracking tracking = new Tracking("RT224265042HK");
    tracking.setSlug("hong-kong-post");
    
	connection.retrack(tracking);
	//You can only retrack an expired tracking and only once


**Get the last checkpoint of a tracking of your account**

	Tracking tracking = new Tracking("GM605112270084510370");
    tracking.setSlug("dhl-global-mail");
    
	Checkpoint newCheckpoint = connection.getLastCheckpoint(tracking);
	newCheckpoint.getMessage()//"Delivered"
	newCheckpoint.getCountryName()//"BUDERIM QLD, AU"
	newCheckpoint.getTag()//"Delivered"


**Be careful, all the operations that use tracking use the ID if it is informed**


	//Post a tracking in your account
	Tracking tracking1 = new Tracking("05167019264110");
    tracking1.setSlug("dpd");
	Tracking trackingPosted =  connection.postTracking(tracking1);
	
	trackingPosted.getId();// this is the ID of the tracking in the Aftership system

	

