package code;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.*;
import java.io.OutputStreamWriter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 10/6/14.
 */
public class ConnectionAPI {

    private static String URL_SERVER = "https://api.aftership.com/";
    private static String VERSION_API = "v3";

    private String keyAPI;

    public ConnectionAPI(String keyAPI) {
        this.keyAPI = keyAPI;
    }

    public Checkpoint getLastCheckpoint(String trackingNumber,String slug)throws Exception{

        JSONObject response = this.request("GET","/last_checkpoint/"+slug+"/"+trackingNumber,null);
        JSONObject checkpointJSON = response.getJSONObject("data").getJSONObject("checkpoint");
        Checkpoint checkpoint = null;
        if(checkpointJSON.length()!=0) {
            checkpoint = new Checkpoint(checkpointJSON);
        }

        return checkpoint;
    }

    public void reactivate(String trackingNumber, String slug)throws Exception{

        JSONObject response = this.request("POST","/trackings/"+slug+"/"+trackingNumber+"/reactivate",null);

    }
    public Tracking getTrackingByNumber(String trackingNumber,String slug)throws Exception{

        JSONObject response = this.request("GET","/trackings/"+slug+"/"+trackingNumber,null);
        JSONObject trackingJSON = response.getJSONObject("data").getJSONObject("tracking");
        Tracking tracking = null;
        if(trackingJSON.length()!=0) {
            tracking = new Tracking(trackingJSON);
        }

        return tracking;
    }

    public int getTracking(ParametersTracking parameters)throws Exception{
        List<Tracking> trackingList = null;
        int size =0;
       System.out.println(parameters.generateQueryString());

        JSONObject response = this.request("GET","/trackings?"+parameters.generateQueryString(),null);
        JSONArray trackingJSON = response.getJSONObject("data").getJSONArray("trackings");
        if(trackingJSON.length()!=0) {
            size = response.getJSONObject("data").getInt("count");
            trackingList = new ArrayList<Tracking>(trackingJSON.length());
            for (int i = 0; i < trackingJSON.length(); i++) {
                trackingList.add(new Tracking(trackingJSON.getJSONObject(i)));
            }
            parameters.setBuffer(trackingList);
            parameters.setTotal(size);
            parameters.setConnectionApi(this);
        }

        return size;
    }

    public List<Tracking> getTracking(int page)throws Exception{

        List<Tracking> trackingList = null;

        JSONObject response = this.request("GET","/trackings?page="+page,null);

        JSONArray trackingJSON = response.getJSONObject("data").getJSONArray("trackings");

        if(trackingJSON.length()!=0) {
             trackingList = new ArrayList<Tracking>(trackingJSON.length());

            for (int i = 0; i < trackingJSON.length(); i++) {
                trackingList.add(new Tracking((JSONObject)trackingJSON.get(i)));
            }
        }

        return trackingList;

    }

    public JSONObject deleteTracking(String slug,String trackingNumber)throws AftershipAPIException,IOException,ParseException{
        JSONObject response = this.request("DELETE","/trackings/"+slug+"/"+trackingNumber,null);
        System.out.println("Deleted number: "+response.getJSONObject("data").getJSONObject("tracking").getString("tracking_number"));

        return response;
    }

    public JSONObject postTracking(Tracking tracking) throws AftershipAPIException,IOException,ParseException {

        JSONObject response = this.request("POST", "/trackings", tracking.generateJSON());

        return response.getJSONObject("data").getJSONObject("tracking");

    }

    public JSONObject putTracking(Tracking tracking) throws AftershipAPIException,IOException,ParseException {

        System.out.println( "/trackings/"+tracking.getSlug()+ "/"+tracking.getTrackingNumber());
        System.out.println(tracking.generatePutJSON());

        JSONObject response = this.request("PUT", "/trackings/"+tracking.getSlug()+
                "/"+tracking.getTrackingNumber(), tracking.generatePutJSON());

        return response.getJSONObject("data").getJSONObject("tracking");

    }
    /**
    * Return a list of couriers supported by AfterShip along with their names,
    * URLs and slugs.
    *
    * @return     A list of Object Courier, with all the couriers returned
     * by the API.
    **/
    public List<Courier> getCouriers() throws AftershipAPIException,IOException,ParseException{

        JSONObject response = this.request("GET","/couriers",null);


        JSONArray couriersJSON = response.getJSONObject("data").getJSONArray("couriers");
        List<Courier> couriers = new ArrayList<Courier>(couriersJSON.length());

        JSONObject element;

        for (int i = 0; i < couriersJSON.length(); i++) {
            element = couriersJSON.getJSONObject(i);

            Courier newCourier = new Courier(element);
            couriers.add(newCourier);
        }
        return couriers;
    }

     /**
     * Get a list of matched couriers for a tracking number based on the tracking number format.
     * note, only check the couriers you have defined in your account
     *
     * @return A JSONObject with the response
     * @exception code.AftershipAPIException if the request response an error
     * @exception code.AftershipAPIException Invalid JSON data. If the tracking number doesn't match any courier
     **/
    public List<Courier> detectCouriers(String trackingNumber)throws AftershipAPIException,IOException,ParseException{
        JSONObject response = this.request("GET","/couriers/detect/"+trackingNumber,null);
       // System.out.println(response);
        List<Courier> couriers = new ArrayList<Courier>();

        JSONArray couriersJSON = response.getJSONObject("data").getJSONArray("couriers");
        JSONObject element;

        for (int i = 0; i < couriersJSON.length(); i++) {
            element = couriersJSON.getJSONObject(i);

            Courier newCourier = new Courier(element);
            couriers.add(newCourier);
        }
        return couriers;
    }


    /**
     * make a request to the HTTP API of aftership
     *
     * @return A JSONObject with the response
     * @exception code.AftershipAPIException if the request response an error
     **/
    public JSONObject request(String method, String url, JSONObject body)
            throws AftershipAPIException,IOException,ParseException{
        BufferedReader rd;
        StringBuilder sb;
        OutputStreamWriter wr;

        HttpURLConnection connection;
        URL serverAddress= new URL(new URL(URL_SERVER),VERSION_API+ url);
        connection= (HttpURLConnection)serverAddress.openConnection();
        connection.setRequestMethod(method);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("aftership-api-key", keyAPI);
        if(body!=null){ connection.setDoOutput(true);}//if there is information in body, doOutput true, to write

        connection.connect();
        if(body!=null){
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(body.toString());
            wr.flush();
            System.out.println("entramos");
        }
//        System.out.println("URL: "+connection.getURL());
//        System.out.println("Method: "+ connection.getRequestMethod());
//        System.out.println("Body: "+ body.toString());
//        System.out.println(connection.getResponseCode()+connection.getResponseMessage());
        this.checkAPIResponse(connection.getResponseCode());

        rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null)
        {
            sb.append(line + '\n');
        }
 //       System.out.println(sb.toString());
        JSONObject response;
        response  = new JSONObject(sb.toString());

        return response;
    }

    /**
     * Check the status of a http response and if the status is an error throws an exception
     *
     * @exception AftershipAPIException A customize exception with a different message
     * depending of the status error
     **/
    public void checkAPIResponse(int status)throws AftershipAPIException{

        switch (status){
            case 200:
            case 201:
                break;
            case 400:
                throw new AftershipAPIException("Invalid JSON data.");
            case 401:
                throw new AftershipAPIException("InvalidCredentials - Invalid API Key.");
            case 402:
                throw new AftershipAPIException("Request Failed - Parameters were valid but request failed.");
            case 404:
                throw new AftershipAPIException("ResourceNotFound - The requested resource does not exist.");
            case 409:
                throw new AftershipAPIException("InvalidArgument - The tracking number already exists.");
            case 500:
            case 502:
            case 503:
            case 504:
                throw new AftershipAPIException("Server errors - something went wrong on AfterShip's end.");

        }

    }

    public void prettyPrintJSON(JSONObject oldJSON){
        try {
            JSONTokener tokener = new JSONTokener(oldJSON.toString()); //tokenize the ugly JSON string
            JSONObject finalResult = new JSONObject(tokener); // convert it to JSON object
            System.out.println(finalResult.toString(4)); // To string method prints it with specified indentation.
        }catch( Exception e){
            System.out.println("exception printing pretty JSON: "+e.getMessage() );
        }
    }



}
