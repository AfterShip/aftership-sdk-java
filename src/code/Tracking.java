package code;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by User on 11/6/14.
 */
public class Tracking {
    /** Tracking number of a shipment. Duplicate tracking numbers, or tracking number with invalid tracking
     * number format will not be accepted. */
    private String trackingNumber;
    /**Unique code of each courier. If you do not specify a slug, Aftership will automatically detect
     * the courier based on the tracking number format and your selected couriers*/
    private String slug;
    /** Email address(es) to receive email notifications. Use comma for multiple emails. */
    private List<String> emails;
    /** Phone number(s) to receive sms notifications. Use comma for multiple emails.
     *  Enter + area code before phone number. */
    private List<String> smes;
    /** Title of the tracking. Default value as trackingNumber */
    private String title;
    /** Customer name of the tracking. */
    private String customerName;
    /** ISO Alpha-3(three letters)to specify the destination of the shipment.
     * If you use postal service to send international shipments, AfterShip will automatically
     * get tracking results at destination courier as well (e.g. USPS for USA). */
    private String destinationCountryISO3;
    /** Text field for order ID */
    private String orderID;
    /** Text field for order path */
    private String orderIDPath;
    /** Custom fields that accept any text string */
    private Map<String,String> customFields;

    public Tracking(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        this.title = trackingNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void addEmails(String emails) {
        if(this.emails==null) {
            this.emails = new ArrayList<String>();
            this.emails.add(emails);
        }
        else {
            this.emails.add(emails);
        }
    }

    public void deleteEmail(String email) {
        if (this.emails!=null) {
            this.emails.remove(email);
        }
    }

    public void deleteEmail(){
        this.emails =null;
    }

    public List<String> getSmes() {
        return smes;
    }

    public void addSmes(String smes) {

        if(this.smes==null) {
            this.smes = new ArrayList<String>();
            this.smes.add(smes);
        }
        else
            this.smes.add(smes);
    }

    public void deleteSmes(String smes) {
        if(this.smes!=null) {
            this.smes.remove(smes);
        }
    }

    public void deleteSmes(){
        this.smes=null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDestinationCountryISO3() {
        return destinationCountryISO3;
    }

    public void setDestinationCountryISO3(String destinationCountryISO3) {
        this.destinationCountryISO3 = destinationCountryISO3;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderIDPath() {
        return orderIDPath;
    }

    public void setOrderIDPath(String orderIDPath) {
        this.orderIDPath = orderIDPath;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public void addCustomFields(String field, String value) {

        if (this.customFields ==null){
            this.customFields = new HashMap<String, String>();
        }
        customFields.put(field,value);
    }

    public void deleteCustomFields(String field) {
        if (this.customFields != null) {
            this.customFields.remove(field);
        }
    }

    public void deleteCustomFields(){
        this.customFields=null;
    }

    public JSONObject generateJSON(){
        JSONObject globalJSON = new JSONObject();
        JSONObject trackingJSON = new JSONObject();
        JSONObject customFieldsJSON;

        trackingJSON.put("tracking_number", this.trackingNumber);
        if(this.slug!=null) trackingJSON.put("slug", this.slug);
        if(this.title!=null) trackingJSON.put("title", this.title);
        if(this.emails!=null) trackingJSON.put("emails",this.emails);
        if(this.smes!=null) trackingJSON.put("smses",this.smes);
        if(this.customerName!=null) trackingJSON.put("custonmer_name",this.customerName);
        if(this.destinationCountryISO3!=null) trackingJSON.put("destination_country_iso3",this.destinationCountryISO3);
        if(this.orderID!=null) trackingJSON.put("order_id",this.orderID);
        if(this.orderIDPath!=null) trackingJSON.put("order_id_path",this.orderIDPath);
        if(this.customFields!=null){
            customFieldsJSON  = new JSONObject();

            for (Map.Entry<String, String> entry : this.customFields.entrySet()) {
                customFieldsJSON.put(entry.getKey(),entry.getValue());
            }
            trackingJSON.put("custom_fields",customFieldsJSON);
        }
        globalJSON.put("tracking",trackingJSON);

        return globalJSON;

    }
}
