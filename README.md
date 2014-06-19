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
	connection.deleteTracking(trackingNumberDelete,slugDelete)


**Get trackings of your account, there is two ways**

	//1- Simplest way, with the page you want to get

	List<Tracking> listTrackings100 = connection.getTracking(1);//get first 100
	List<Tracking> listTrackings200 = connection.getTracking(2);//get 100-200
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

	//Return the number of trackings in your account from dhl and created less than a month ago.
	int totalDHL =connection.getTracking(param);

	//For actually get the trackings we use the parameter created
    Tracking aux;
	while(param.hasNext()){//Check if there is more trackings
		aux = param.next();//Get the next tracking
		aux.getTrackingNumber();//Here you can iterate on all the result
	}

	//Other examples

	//Get trackings with destination Spain
	ParametersTracking param1 = new ParametersTracking();
    param1.addDestination(ISO3Country.ESP);
    int totalSpain =connection.getTracking(param1);

	//Get trackings that are OutForDelivery
    ParametersTracking param2 = new ParametersTracking();
    param2.addTag(StatusTag.OutForDelivery);
    int totalOutDelivery=connection.getTracking(param2);

	//Get all the elements in your account and print their tracking number
    ParametersTracking param3 = new ParametersTracking();//We don't add any value to params
    int totalTotal = connection.getTracking(param3);
    System.out.println(totalTotal);
    int number=0;
    //We don't have to worry about the page, only need to call hasNext() and next()
    while(param3.hasNext()){
        System.out.println(++number +". "+param3.next().getTrackingNumber());
    }


**Get a tracking from your account**

	Tracking tracking = connection.getTrackingByNumber("RC328021065CN","canada-post");


**Modify a tracking from your account**

	//Create a tracking
	Tracking tracking = new Tracking("RC328021065CN");
    tracking.setSlug("canada-post");
    //Add the fields we want to modify
    tracking.setTitle("another title");

	//Returns a tracking with exactly the information of the server
	Tracking tracking2 = connection.putTracking(tracking);
	tracking2.getTitle();//Value “another title”


**Reactivate a tracking of your account**

	connection.reactivate("RT224265042HK","hong-kong-post");
	//You can only reactivate an expired tracking


**Get the last checkpoint of a tracking of your account**

	Checkpoint newCheckpoint = connection.getLastCheckpoint("GM605112270084510370", "dhl-global-mail");
	newCheckpoint.getMessage()//"Delivered"
	newCheckpoint.getCountryName()//"BUDERIM QLD, AU"
	newCheckpoint.getTag()//"Delivered"

