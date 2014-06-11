package code;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.*;

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

    public void postTracking(Tracking tracking){


    }

    /**
    * Return a list of couriers supported by AfterShip along with their names,
    * URLs and slugs.
    *
    * @return     A list of Object Courier, with all the couriers returned
     * by the API.
    **/
    public List<Courier> getCouriers() throws AftershipAPIException,IOException,ParseException{

        JSONObject response = this.request("GET","/couriers","");

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
     * Get a list of matched couriers for a tracking number based on the tracking number format.
     * note, only check the couriers you have defined in your account
     *
     * @return A JSONObject with the response
     * @exception code.AftershipAPIException if the request response an error
     * @exception code.AftershipAPIException Invalid JSON data. If the tracking number doesn't match any courier
     **/
    public List<Courier> detectCouriers(String trackingNumber)throws AftershipAPIException,IOException,ParseException{
        JSONObject response = this.request("GET","/couriers/detect/"+trackingNumber,"");
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
    public JSONObject request(String method, String url, String params)
            throws AftershipAPIException,IOException,ParseException{

        HttpURLConnection connection;
        URL serverAddress= new URL(new URL(URL_SERVER),VERSION_API+ url);
        connection= (HttpURLConnection)serverAddress.openConnection();
        connection.setRequestMethod(method);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("aftership-api-key", keyAPI);
        connection.connect();
//        System.out.println(connection.getURL());
//        System.out.println(connection.getResponseCode()+connection.getResponseMessage());
        this.checkAPIResponse(connection.getResponseCode());

        BufferedReader rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null)
        {
            sb.append(line + '\n');
        }
//        System.out.println(sb.toString());
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



}
