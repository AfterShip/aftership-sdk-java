package code;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by User on 10/6/14.
 */
public class Checkpoint {

    /** Date and time of the tracking created. */
    private String createdAt;

    /** Date and time of the checkpoint, provided by courier. Value may be:
     Empty String,
     YYYY-MM-DD,
     YYYY-MM-DDTHH:MM:SS, or
     YYYY-MM-DDTHH:MM:SS+TIMEZONE */
    private String checkpointTime;

    /** Location info (if any) */
    private String city;

    /** Country ISO Alpha-3 (three letters) of the checkpoint */
    private String countryISO3;

    /** Country name of the checkpoint, may also contain other location info. */
    private String countryName;

    /** Checkpoint message */
    private String message;

    /** Location info (if any) */
    private String state;

    /** Status of the checkpoint */
    private String tag;

    /** Location info (if any) */
    private String zip;

    public Checkpoint(JSONObject checkPointJSON){

        this.createdAt = checkPointJSON.isNull("created_at")?null:checkPointJSON.getString("created_at");
        this.checkpointTime = checkPointJSON.isNull("checkpoint_time")?null:checkPointJSON.getString("checkpoint_time");
        this.city = checkPointJSON.isNull("city")?null:checkPointJSON.getString("city");
        this.countryISO3 = checkPointJSON.isNull("country_iso_3")?null:checkPointJSON.getString("country_iso3");
        this.countryName = checkPointJSON.isNull("country_name")?null:checkPointJSON.getString("country_name");
        this.message = checkPointJSON.isNull("message")?null:checkPointJSON.getString("message");
        this.state = checkPointJSON.isNull("state")?null:checkPointJSON.getString("state");
        this.tag = checkPointJSON.isNull("tag")?null:checkPointJSON.getString("tag");
        this.zip = checkPointJSON.isNull("zip")?null:checkPointJSON.getString("zip");

    }

    @Override
    public String toString() {
        return "Checkpoint{" +
                "createdAt='" + createdAt + '\'' +
                ", checkpointTime='" + checkpointTime + '\'' +
                ", city='" + city + '\'' +
                ", countryISO3='" + countryISO3 + '\'' +
                ", countryName='" + countryName + '\'' +
                ", message='" + message + '\'' +
                ", state='" + state + '\'' +
                ", tag='" + tag + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }
}



