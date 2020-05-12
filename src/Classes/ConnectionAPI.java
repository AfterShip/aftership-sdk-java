package Classes;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import Classes.query.ExportTrackingResponse;
import Enums.FieldCheckpoint;
import Enums.FieldTracking;
import org.json.*;

import java.io.OutputStreamWriter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Enums.*;

/**
 * ConnectionAPI is the class responsible of the iteration with the HTTP API of Aftership, it wrap all the
 * funcntionalities in different methods
 * Created by User on 10/6/14
 */
public class ConnectionAPI {

    private static String URL_SERVER = "https://api.aftership.com/";
    private static String VERSION_API = "v4";

    private String keyAPI;
    private String apiHost;
    private String apiVersion;

    public ConnectionAPI(String keyAPI) {
        this.keyAPI = keyAPI;
        this.apiHost = URL_SERVER;
        this.apiVersion = VERSION_API;
    }

    public ConnectionAPI(String keyAPI, String apiHost, String apiVersion) {
        this.keyAPI = keyAPI;
        this.apiHost = apiHost;
        this.apiVersion = apiVersion;
    }

    /**
     * Export trackings from your account with the ParametersTrackingExport defined in the params
     *
     * @param parameters ParametersTrackingExport Object, with the information to get
     * @return A {@link ExportTrackingResponse} object that holds the tracking list and cursor information
     * @throws Classes.AftershipAPIException If the request response an error
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     * @see ParametersTracking
     * @see Tracking
     **/
    public ExportTrackingResponse exportTrackings(ParametersTrackingExport parameters)
            throws AftershipAPIException, IOException, ParseException, JSONException {
        List<Tracking> trackingList = null;

        String requestParams = parameters.generateQueryString();
        JSONObject response = this.request("GET", "/trackings/exports" + requestParams, null);

        String cursor = response.getJSONObject("data").getString("cursor");
        JSONArray trackingJSON = response.getJSONObject("data").getJSONArray("trackings");

        if (trackingJSON.length() > 0) {
            trackingList = new ArrayList<Tracking>();

            for (int i = 0; i < trackingJSON.length(); i++) {
                trackingList.add(new Tracking(trackingJSON.getJSONObject(i)));
            }
        }

        return new ExportTrackingResponse(trackingList, cursor);
    }

    /**
     * Return the tracking information of the last checkpoint of a single tracking
     *
     * @param tracking A Tracking to get the last checkpoint of, it should have tracking number and slug at least.
     * @return The last Checkpoint object
     * @throws Classes.AftershipAPIException If the request response an error
     *                                       The tracking is not defined in your account
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     * @see Checkpoint
     **/
    public Checkpoint getLastCheckpoint(Tracking tracking)
            throws AftershipAPIException, IOException, ParseException, JSONException {

        String parametersExtra = "";
        if (tracking.getId() != null && !(tracking.getId().compareTo("") == 0)) {
            parametersExtra = tracking.getId();
        } else {
            String paramRequiredFields = tracking.getQueryRequiredFields().replaceFirst("&", "?");
            parametersExtra = tracking.getSlug() + "/" + tracking.getTrackingNumber() + paramRequiredFields;
        }

        JSONObject response = this.request("GET", "/last_checkpoint/" + parametersExtra, null);

        JSONObject checkpointJSON = response.getJSONObject("data").getJSONObject("checkpoint");
        Checkpoint checkpoint = null;
        if (checkpointJSON.length() != 0) {
            checkpoint = new Checkpoint(checkpointJSON);
        }

        return checkpoint;
    }

    /**
     * Return the tracking information of the last checkpoint of a single tracking
     *
     * @param tracking A Tracking to get the last checkpoint of, it should have tracking number and slug at least.
     * @param fields   A list of fields of checkpoint wanted to be in the response
     * @param lang     A String with the language desired. Support Chinese to English translation
     *                 for china-ems and china-post only
     * @return The last Checkpoint object
     * @throws Classes.AftershipAPIException If the request response an error
     *                                       The tracking is not defined in your account
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     * @see Checkpoint
     **/
    public Checkpoint getLastCheckpoint(Tracking tracking, List<FieldCheckpoint> fields, String lang)
            throws AftershipAPIException, IOException, ParseException, JSONException {

        String params;
        QueryString qs = new QueryString();

        if (fields != null) qs.add("fields", fields);
        if (lang != null && !lang.equals("")) qs.add("lang", lang);
        params = qs.toString().replaceFirst("&", "?");

        String parametersExtra = "";
        if (tracking.getId() != null && !(tracking.getId().compareTo("") == 0)) {
            parametersExtra = tracking.getId() + params;
        } else {
            String paramRequiredFields = tracking.getQueryRequiredFields();
            parametersExtra = tracking.getSlug() + "/" + tracking.getTrackingNumber() + params + paramRequiredFields;
        }

        JSONObject response = this.request("GET", "/last_checkpoint/" + parametersExtra, null);

        JSONObject checkpointJSON = response.getJSONObject("data").getJSONObject("checkpoint");
        Checkpoint checkpoint = null;
        if (checkpointJSON.length() != 0) {
            checkpoint = new Checkpoint(checkpointJSON);
        }

        return checkpoint;
    }


    /**
     * Retrack an expired tracking once
     *
     * @param tracking A Tracking to reactivate, it should have tracking number and slug at least.
     * @return A JSONObject with the response. It will contain the status code of the operation, trackingNumber,
     * slug and active (to true)
     * @throws Classes.AftershipAPIException If the request response an error
     *                                       The tracking is not defined in your account
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     **/
    public boolean retrack(Tracking tracking)
            throws AftershipAPIException, IOException, ParseException, JSONException {

        String paramRequiredFields = tracking.getQueryRequiredFields().replaceFirst("&", "?");

        String url = "/trackings/" + tracking.getSlug() +
                "/" + tracking.getTrackingNumber() + "/retrack" + paramRequiredFields;

        JSONObject response = this.request("POST", "/trackings/" + tracking.getSlug() +
                "/" + tracking.getTrackingNumber() + "/retrack" + paramRequiredFields, null);

        if (response.getJSONObject("meta").getInt("code") == 200) {
            if (response.getJSONObject("data").getJSONObject("tracking").getBoolean("active")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * Get a specific tracking from your account
     *
     * @param trackingGet A Tracking to get, it should have tracking number and slug at least.
     * @return A Tracking object with the response
     * @throws Classes.AftershipAPIException If the request response an error
     *                                       The tracking is not defined in your account
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     * @see Tracking
     **/
    public Tracking getTrackingByNumber(Tracking trackingGet)
            throws AftershipAPIException, IOException, ParseException, JSONException {
        String parametersExtra = "";

        if (trackingGet.getId() != null && !(trackingGet.getId().compareTo("") == 0)) {
            parametersExtra = trackingGet.getId();
        } else {
            String paramRequiredFields = trackingGet.getQueryRequiredFields().replaceFirst("&", "?");
            parametersExtra = trackingGet.getSlug() +
                    "/" + trackingGet.getTrackingNumber() + paramRequiredFields;
        }

        JSONObject response = this.request("GET", "/trackings/" + parametersExtra, null);
        JSONObject trackingJSON = response.getJSONObject("data").getJSONObject("tracking");
        Tracking tracking = null;
        if (trackingJSON.length() != 0) {
            tracking = new Tracking(trackingJSON);
        }

        return tracking;
    }

    /**
     * Get a specific tracking from your account
     *
     * @param trackingGet A Tracking to get, it should have tracking number and slug at least.
     * @param fields      A list of fields wanted to be in the response
     * @param lang        A String with the language desired. Support Chinese to English translation
     *                    for china-ems and china-post only
     *                    (Example: en)
     * @return A Tracking object with the response
     * @throws Classes.AftershipAPIException If the request response an error
     *                                       The tracking is not defined in your account
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     * @see Tracking
     **/
    public Tracking getTrackingByNumber(Tracking trackingGet, List<FieldTracking> fields, String lang)
            throws AftershipAPIException, IOException, ParseException, JSONException {


        String params;
        QueryString qs = new QueryString();
        if (fields != null) qs.add("fields", fields);
        if (lang != null && !lang.equals("")) qs.add("lang", lang);
        params = qs.toString().replaceFirst("&", "?");

        String parametersExtra = "";
        if (trackingGet.getId() != null && !(trackingGet.getId().compareTo("") == 0)) {
            parametersExtra = trackingGet.getId() + params;
        } else {
            String paramRequiredFields = trackingGet.getQueryRequiredFields();
            parametersExtra = trackingGet.getSlug() + "/" + trackingGet.getTrackingNumber() + params + paramRequiredFields;
        }

        JSONObject response = this.request("GET", "/trackings/" + parametersExtra, null);
        JSONObject trackingJSON = response.getJSONObject("data").getJSONObject("tracking");
        Tracking tracking = null;
        if (trackingJSON.length() != 0) {
            tracking = new Tracking(trackingJSON);
        }

        return tracking;
    }

    /**
     * Get trackings from your account with the ParametersTracking defined in the params
     *
     * @param parameters ParametersTracking Object, with the information to get
     * @return A Tracking List with the trackings that match the values of ParametersTracking in param,
     * accessing the trackings should be made through the ParametersTracking passed as param
     * @throws Classes.AftershipAPIException If the request response an error
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     * @see ParametersTracking
     * @see Tracking
     **/
    public List<Tracking> getTrackings(ParametersTracking parameters)
            throws AftershipAPIException, IOException, ParseException, JSONException {
        List<Tracking> trackingList = null;
        int size = 0;
        JSONObject response = this.request("GET", "/trackings?" + parameters.generateQueryString(), null);
        JSONArray trackingJSON = response.getJSONObject("data").getJSONArray("trackings");
        if (trackingJSON.length() != 0) {
            size = response.getJSONObject("data").getInt("count");
            trackingList = new ArrayList<Tracking>(trackingJSON.length());
            for (int i = 0; i < trackingJSON.length(); i++) {
                trackingList.add(new Tracking(trackingJSON.getJSONObject(i)));
            }
            parameters.setTotal(size);
        }
        return trackingList;
    }

    /**
     * Get next page of Trackings from your account with the ParametersTracking defined in the params
     *
     * @param parameters ParametersTracking Object, with the information to get
     * @return The next page of Tracking List that match then values of ParametersTracking in param,
     * accessing the trackings should be made through the ParametersTracking passed as param
     * @throws Classes.AftershipAPIException If the request response an error
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     * @see ParametersTracking
     * @see Tracking
     **/
    public List<Tracking> getTrackingsNext(ParametersTracking parameters)
            throws AftershipAPIException, IOException, ParseException, JSONException {
        parameters.setPage(parameters.getPage() + 1);
        return this.getTrackings(parameters);
    }

    /**
     * Get as much as 100 trackings from your account, created less than 30 days ago. If you delete right before,
     * you may obtain less than 100 trackings.
     *
     * @param page Indicated the page of 100 trackings to return, if page is 1 will return the first 100, if is 2
     *             100-200 etc
     * @return A List of Tracking Objects from your account. Max 100 trackings
     * @throws Classes.AftershipAPIException If the request response an error
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     * @see Tracking
     **/
    public List<Tracking> getTrackings(int page) throws AftershipAPIException, IOException, ParseException, JSONException {

        List<Tracking> trackingList = null;

        JSONObject response = this.request("GET", "/trackings?limit=100&page=" + page, null);
        JSONArray trackingJSON = response.getJSONObject("data").getJSONArray("trackings");
        if (trackingJSON.length() != 0) {
            trackingList = new ArrayList<Tracking>(trackingJSON.length());

            for (int i = 0; i < trackingJSON.length(); i++) {
                trackingList.add(new Tracking((JSONObject) trackingJSON.get(i)));
            }
        }
        return trackingList;

    }

    /**
     * Delete a tracking from your account
     *
     * @param tracking A Tracking to delete
     * @return A boolean, true if delete correctly, and false otherwise
     * @throws Classes.AftershipAPIException If the request response an error
     *                                       The tracking is not defined in your account
     * @throws java.io.IOException           If there is a problem with the connection
     * @throws java.text.ParseException      If the response can not be parse to JSONObject
     **/
    public boolean deleteTracking(Tracking tracking)
            throws AftershipAPIException, IOException, ParseException, JSONException {

        String parametersExtra = "";
        if (tracking.getId() != null && !(tracking.getId().compareTo("") == 0)) {
            parametersExtra = tracking.getId();
        } else {
            String paramRequiredFields = tracking.getQueryRequiredFields().replaceFirst("&", "?");
            parametersExtra = tracking.getSlug() + "/" + tracking.getTrackingNumber() + paramRequiredFields;
        }

        JSONObject response = this.request("DELETE", "/trackings/" + parametersExtra, null);
        if (response.getJSONObject("meta").getInt("code") == 200)
            return true;
        else
            return false;
    }

    /**
     * Add a new tracking to your account
     *
     * @param tracking A Tracking object with the information to creates
     *                 The field trackingNumber SHOULD be informed, otherwise an exception will be thrown
     *                 The fields an user can add are: slug, smses, emails, title, customerName, orderID, orderIDPath,
     *                 customFields, destinationCountryISO3 (the others are provided by the Server)
     * @return A Tracking object with the fields in the same state as the server, if a field has an error,
     * it won't be added, and won't be shown in the response (for example if the smses
     * phone number is not valid). This response doesn't have checkpoints informed!
     * @throws AftershipAPIException    If the request response an error
     *                                  Duplicate trackingNumbers, or trackingNumber with invalid format will not be accepted
     * @throws java.io.IOException      If there is a problem with the connection
     * @throws java.text.ParseException If the response can not be parse to JSONObject
     **/
    public Tracking postTracking(Tracking tracking)
            throws AftershipAPIException, IOException, ParseException, JSONException {

        JSONObject response = this.request("POST", "/trackings", tracking.generateJSON());

        return new Tracking(response.getJSONObject("data").getJSONObject("tracking"));

    }

    /**
     * Updates a tracking of your account
     *
     * @param tracking A Tracking object with the information to update
     *                 The fields trackingNumber and slug SHOULD be informed, otherwise an exception will be thrown
     *                 The fields an user can update are: smses, emails, title, customerName, orderID, orderIDPath,
     *                 customFields
     * @return A Tracking object with the fields in the same state as the server, if a field has an error,
     * it won't be updated, and won't be shown in the response (for example if the smses
     * phone number is not valid). This response doesn't have checkpoints informed!
     * @throws AftershipAPIException    If the request response an error
     *                                  If the Tracking doesn't have informed trackingNumber and slug an exception will be thrown
     * @throws java.io.IOException      If there is a problem with the connection
     * @throws java.text.ParseException If the response can not be parse to JSONObject
     **/
    public Tracking putTracking(Tracking tracking) throws AftershipAPIException, IOException, ParseException, JSONException {

        String parametersExtra = "";
        if (tracking.getId() != null && !(tracking.getId().compareTo("") == 0)) {
            parametersExtra = tracking.getId();
        } else {
            String paramRequiredFields = tracking.getQueryRequiredFields().replaceFirst("&", "?");
            parametersExtra = tracking.getSlug() + "/" + tracking.getTrackingNumber() + paramRequiredFields;
        }


        JSONObject response = this.request("PUT", "/trackings/" + parametersExtra, tracking.generatePutJSON());

        return new Tracking(response.getJSONObject("data").getJSONObject("tracking"));

    }

    /**
     * Return a list of couriers supported by AfterShip along with their names,
     * URLs and slugs
     *
     * @return A list of Object Courier, with all the couriers supported by the API
     * @throws AftershipAPIException    If the request response an error
     * @throws java.io.IOException      If there is a problem with the connection
     * @throws java.text.ParseException If the response can not be parse to JSONObject
     **/
    public List<Courier> getAllCouriers() throws AftershipAPIException, IOException, ParseException, JSONException {

        JSONObject response = this.request("GET", "/couriers/all", null);


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
     * Return a list of user couriers enabled by user's account along their names, URLs, slugs, required fields.
     *
     * @return A list of Object Courier, with all the couriers supported by the API
     * @throws AftershipAPIException    If the request response an error
     * @throws java.io.IOException      If there is a problem with the connection
     * @throws java.text.ParseException If the response can not be parse to JSONObject
     **/
    public List<Courier> getCouriers() throws AftershipAPIException, IOException, ParseException, JSONException {

        JSONObject response = this.request("GET", "/couriers", null);


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
     * Get a list of matched couriers for a tracking number based on the tracking number format
     * Note, only check the couriers you have defined in your account
     *
     * @param trackingNumber tracking number to match with couriers
     * @return A List of Couriers objects that match the provided trackingNumber
     * @throws AftershipAPIException    if the request response an error
     *                                  Invalid JSON data. If the tracking number doesn't match any courier defined in your account,
     *                                  or it doesn't match any courier defined in Aftership
     * @throws java.io.IOException      If there is a problem with the connection
     * @throws java.text.ParseException If the response can not be parse to JSONObject
     **/
    public List<Courier> detectCouriers(String trackingNumber)
            throws AftershipAPIException, IOException, ParseException, JSONException {
        JSONObject body = new JSONObject();
        JSONObject tracking = new JSONObject();

        if (trackingNumber == null || trackingNumber.equals(""))
            throw new AftershipAPIException("the tracking number should be always informed for the method detectCouriers");
        tracking.put("tracking_number", trackingNumber);
        body.put("tracking", tracking);
        JSONObject response = this.request("POST", "/couriers/detect", body);
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
     * Get a list of matched couriers for a tracking number based on the tracking number format
     * Note, only check the couriers you have defined in your account
     *
     * @param trackingNumber        Tracking number to match with couriers (mandatory)
     * @param trackingPostalCode    The postal code of the ship to address. Required by some couriers, such as `dx` (optional)
     * @param trackingShipDate      Usually it is refer to the posting date of the shipment, format in YYYYMMDD.
     *                              Required by some couriers, such as `deutsch-post`.(optional)
     * @param trackingAccountNumber The account number for particular courier. Required by some couriers,
     *                              such as `dynamic-logistics`.(optional)
     * @param slugs                 The slug of couriers to detect.
     * @return A List of Couriers objects that match the provided trackingNumber
     * @throws AftershipAPIException    if the request response an error
     *                                  Invalid JSON data. If the tracking number doesn't match any courier defined in your account,
     *                                  or it doesn't match any courier defined in Aftership
     * @throws java.io.IOException      If there is a problem with the connection
     * @throws java.text.ParseException If the response can not be parse to JSONObject
     **/
    public List<Courier> detectCouriers(String trackingNumber, String trackingPostalCode, String trackingShipDate,
                                        String trackingAccountNumber, List<String> slugs)
            throws AftershipAPIException, IOException, ParseException, JSONException {
        JSONObject body = new JSONObject();
        JSONObject tracking = new JSONObject();

        if (trackingNumber == null || trackingNumber.equals(""))
            throw new AftershipAPIException("Tracking number should be always informed for the method detectCouriers");
        tracking.put("tracking_number", trackingNumber);

        if (trackingPostalCode != null && !trackingPostalCode.equals(""))
            tracking.put("tracking_postal_code", trackingPostalCode);
        if (trackingShipDate != null && !trackingShipDate.equals(""))
            tracking.put("tracking_ship_date", trackingShipDate);
        if (trackingAccountNumber != null && !trackingAccountNumber.equals(""))
            tracking.put("tracking_account_number", trackingAccountNumber);

        if (slugs != null && slugs.size() != 0) {

            JSONArray slugsJSON = new JSONArray(slugs);
            tracking.put("slug", slugsJSON);
        }

        body.put("tracking", tracking);

        JSONObject response = this.request("POST", "/couriers/detect", body);
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
     * make a request to the HTTP API of Aftership
     *
     * @param method String with the method of the request: GET, POST, PUT, DELETE
     * @param url    String with the URL of the request
     * @param body   JSONObject with the body of the request, if the request doesn't need body "GET/DELETE", the body
     *               would be null
     * @return A JSONObject with the response of the request
     * @throws AftershipAPIException    If the request response an error
     * @throws java.io.IOException      If there is a problem with the connection
     * @throws java.text.ParseException If the response can not be parse to JSONObject
     **/
    public JSONObject request(String method, String url, JSONObject body)
            throws AftershipAPIException, IOException, ParseException, JSONException {
        BufferedReader rd;
        StringBuilder sb;
        OutputStreamWriter wr;

        HttpURLConnection connection;
        URL serverAddress = new URL(new URL(this.apiHost), this.apiVersion + url);

        connection = (HttpURLConnection) serverAddress.openConnection();
        connection.setRequestMethod(method);
        connection.setReadTimeout(10000);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("aftership-api-key", keyAPI);

        if (body != null) {
            connection.setDoOutput(true);
        }//if there is information in body, doOutput true, to write

        connection.connect();
        if (body != null) {
            wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(body.toString());
            wr.flush();
        }

        this.checkAPIResponse(connection.getResponseCode(), connection);
        rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        sb = new StringBuilder();

        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line + '\n');
        }

        JSONObject response;
        response = new JSONObject(sb.toString());


        return response;
    }

    /**
     * Check the status of a http response and if the status is an error throws an exception
     *
     * @param status Status of the connection response
     * @throws AftershipAPIException A customize exception with a message
     *                               depending of the status error
     **/
    public void checkAPIResponse(int status, HttpURLConnection connection)
            throws AftershipAPIException, IOException, ParseException, JSONException {

        if (status > 201) {

            BufferedReader rd;
            StringBuilder sb;
            String message = "";
            String type = "";
            rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
//            JSONObject response = new JSONObject(sb.toString());
//            JSONObject meta = response.has("meta")?response.getJSONObject("meta"):new JSONObject();
//            JSONObject data = response.has("data")?response.getJSONObject("data"):new JSONObject();
//            message = meta.has("message")?meta.getString("message"):"";
//            type = meta.has("type")?meta.getString("type"):"";
//            StringBuilder newInformation = new StringBuilder();
//            Iterator<?> keys = data.keys();
//            String key;
//            while( keys.hasNext() ) {
//                key = (String) keys.next();
//                newInformation.append(" " + key + " = " + data.getString(key));
//            }

            throw new AftershipAPIException(sb.toString().trim());
        }

    }

    public void prettyPrintJSON(JSONObject oldJSON) {
        try {
            JSONTokener tokener = new JSONTokener(oldJSON.toString()); //tokenize the ugly JSON string
            JSONObject finalResult = new JSONObject(tokener); // convert it to JSON object
            System.out.println(finalResult.toString(4)); // To string method prints it with specified indentation.
        } catch (Exception e) {
            System.out.println("exception printing pretty JSON: " + e.getMessage());
        }
    }


}
