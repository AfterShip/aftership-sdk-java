package code;

import org.json.JSONArray;

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



}



