package code;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 10/6/14.
 */
public class ConnectionAPI {

    private static String URL_SERVER = "https://api.aftership.com/";
    private static String VERSION_API = "v3";

    private String keyAPI = "a61d6204-6477-4f6d-93ec-86c4f872fb6b";

    public ConnectionAPI(String keyAPI) {
      //  this.keyAPI = keyAPI;
    }


    public List<Courier> getCouriers() throws Exception{


        HttpURLConnection connection_get;

        URL serverAddress= new URL(new URL(URL_SERVER),VERSION_API+ "/couriers");
        connection_get= (HttpURLConnection)serverAddress.openConnection();
        connection_get.setRequestMethod("GET");
        connection_get.setReadTimeout(10000);
        connection_get.setRequestProperty("Accept", "application/json");
        connection_get.setRequestProperty("aftership-api-key", keyAPI);

        connection_get.connect();
        int status = connection_get.getResponseCode();
        if( status == 200){
            System.out.println("esto tiene buena pinta");
        }
        BufferedReader rd  = new BufferedReader(new InputStreamReader(connection_get.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null)
        {
            sb.append(line + '\n');
        }
        System.out.println(sb.toString());
        JSONObject response;
        response  = new JSONObject(sb.toString());

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


}
