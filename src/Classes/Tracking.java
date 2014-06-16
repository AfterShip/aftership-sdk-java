package Classes;

import Enums.ISO3Country;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Define a Tracking element
 * Created by User on 11/6/14
 */
public class Tracking {

    /**Tracking number of a shipment. Duplicate tracking numbers, or tracking number with invalid tracking
     * number format will not be accepted. */
    private String trackingNumber;

    /**Unique code of each courier. If you do not specify a slug, Aftership will automatically detect
     * the courier based on the tracking number format and your selected couriers*/
    private String slug;

    /** Email address(es) to receive email notifications. Use comma for multiple emails. */
    private List<String> emails;

    /** Phone number(s) to receive sms notifications. Use comma for multiple emails.
     * Enter + area code before phone number. */
    private List<String> smses;

    /** Title of the tracking. Default value as trackingNumber */
    private String title;

    /** Customer name of the tracking. */
    private String customerName;

    /** ISO Alpha-3(three letters)to specify the destination of the shipment.
     * If you use postal service to send international shipments, AfterShip will automatically
     * get tracking results at destination courier as well (e.g. USPS for USA). */
    private ISO3Country destinationCountryISO3;

    /**  Origin country of the tracking. ISO Alpha-3 */
    private ISO3Country originCountryISO3;

    /** Text field for order ID */
    private String orderID;

    /** Text field for order path */
    private String orderIDPath;

    /** Custom fields that accept any TEXT STRING*/
    private Map<String, String> customFields;

    /** fields informed by Aftership API*/

    /**  Date and time of the tracking created. */
    private String createdAt;

    /** Date and time of the tracking last updated. */
    private String updatedAt;

    /** Whether or not AfterShip will continue tracking the shipments.
     * Value is `false` when status is `Delivered` or `Expired`. */
    private boolean active;

    /** Expected delivery date (if any). */
    private String expectedDelivery;

    /**  Number	Number of packages under the tracking. */
    private int shipmentPackageCount;

    /** Shipment type provided by carrier (if any). */
    private String shipmentType;

    /** Signed by information for delivered shipment (if any). */
    private String signedBy;

    /**  Source of how this tracking is added.  */
    private String source;

    /** Current status of tracking. */
    private String tag;

    /**  Number of attempts AfterShip tracks at courier's system. */
    private int trackedCount;

    /** Array of Hash describes the checkpoint information. */
    List<Checkpoint> checkpoints;


    public Tracking(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        this.title = trackingNumber;
    }

    public Tracking(JSONObject trackingJSON){

        //fields that can be updated by the user

        this.trackingNumber = trackingJSON.isNull("tracking_number")?null:trackingJSON.getString("tracking_number");
        this.slug= trackingJSON.isNull("slug")?null:trackingJSON.getString("slug");
        this.title = trackingJSON.isNull("title")?null:trackingJSON.getString("title");
        this.customerName = trackingJSON.isNull("customer_name")?null:trackingJSON.getString("customer_name");
        this.destinationCountryISO3 = trackingJSON.isNull("destination_country_iso3")?
                null:ISO3Country.valueOf(trackingJSON.getString("destination_country_iso3"));
        this.orderID = trackingJSON.isNull("order_id")?null:trackingJSON.getString("order_id");
        this.orderIDPath = trackingJSON.isNull("order_id_path")?null:trackingJSON.getString("order_id_path");

        JSONArray smsesArray =trackingJSON.isNull("smses")?null:trackingJSON.getJSONArray("smses");
        if(smsesArray !=null && smsesArray.length()!=0){
            this.smses = new ArrayList<String>();
            for (int i=0;i<smsesArray.length();i++){
                this.smses.add(smsesArray.get(i).toString());
            }
        }

        JSONArray emailsArray = trackingJSON.isNull("emails")?null: trackingJSON.getJSONArray("emails");
        if(emailsArray!=null && emailsArray.length()!=0){
            this.emails = new ArrayList<String>();
            for (int i=0;i<emailsArray.length();i++){
                this.emails.add(emailsArray.get(i).toString());
            }
        }

        JSONObject customFieldsJSON =trackingJSON.isNull("custom_fields")?null:trackingJSON.getJSONObject("custom_fields");
        if(customFieldsJSON!=null){
            this.customFields = new HashMap<String, String>();
            Iterator<?> keys = customFieldsJSON.keys();
            while( keys.hasNext() ) {
                String key = (String) keys.next();
                this.customFields.put(key,customFieldsJSON.getString(key));
            }
        }

        //fields that can't be updated by the user, only retrieve

        this.createdAt = trackingJSON.isNull("created_at")?null:trackingJSON.getString("created_at");
        this.updatedAt = trackingJSON.isNull("updated_at")?null:trackingJSON.getString("updated_at");
        this.active = !trackingJSON.isNull("active") && trackingJSON.getBoolean("active");
        this.expectedDelivery = trackingJSON.isNull("expected_delivery")?null:trackingJSON.getString("expected_delivery");
        this.originCountryISO3 = trackingJSON.isNull("origin_country_iso3")?null:
                ISO3Country.valueOf(trackingJSON.getString("origin_country_iso3"));
        this.shipmentPackageCount =  trackingJSON.isNull("shipment_package_count")?0:trackingJSON.getInt("shipment_package_count");
        this.shipmentType = trackingJSON.isNull("shipment_type")?null:trackingJSON.getString("shipment_type");
        this.signedBy = trackingJSON.isNull("singned_by")?null:trackingJSON.getString("signed_by");
        this.source = trackingJSON.isNull("source")?null:trackingJSON.getString("source");
        this.tag = trackingJSON.isNull("tag")?null:trackingJSON.getString("tag");
        this.trackedCount = trackingJSON.isNull("tracked_count")?0:trackingJSON.getInt("tracked_count");

       // checkpoints
        JSONArray checkpointsArray =  trackingJSON.isNull("checkpoints")?null:trackingJSON.getJSONArray("checkpoints");
        if(checkpointsArray!=null && checkpointsArray.length()!=0){
            this.checkpoints = new ArrayList<Checkpoint>();
            for (int i=0;i<checkpointsArray.length();i++){
                this.checkpoints.add(new Checkpoint((JSONObject)checkpointsArray.get(i)));
            }
        }
    }

    public String getTrackingNumber() {
        return trackingNumber;
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
        if (this.emails == null) {
            this.emails = new ArrayList<String>();
            this.emails.add(emails);
        } else {
            this.emails.add(emails);
        }
    }

    public void deleteEmail(String email) {
        if (this.emails != null) {
            this.emails.remove(email);
        }
    }

    public void deleteEmail() {
        this.emails = null;
    }

    public List<String> getSmses() {
        return smses;
    }

    public void addSmses(String smes) {

        if (this.smses == null) {
            this.smses = new ArrayList<String>();
            this.smses.add(smes);
        } else
            this.smses.add(smes);
    }

    public void deleteSmes(String smes) {
        if (this.smses != null) {
            this.smses.remove(smes);
        }
    }

    public void deleteSmes() {
        this.smses = null;
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

    public ISO3Country getDestinationCountryISO3() {
        return destinationCountryISO3;
    }

    public void setDestinationCountryISO3(ISO3Country destinationCountryISO3) {
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

        if (this.customFields == null) {
            this.customFields = new HashMap<String, String>();
        }
        customFields.put(field, value);
    }

    public void deleteCustomFields(String field) {
        if (this.customFields != null) {
            this.customFields.remove(field);
        }
    }

    public void deleteCustomFields() {
        this.customFields = null;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public boolean isActive() {
        return active;
    }

    public String getExpectedDelivery() {
        return expectedDelivery;
    }

    public ISO3Country getOriginCountryISO3() {
        return originCountryISO3;
    }

    public int getShipmentPackageCount() {
        return shipmentPackageCount;
    }

    public String getShipmentType() {
        return shipmentType;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public String getSource() {
        return source;
    }

    public String getTag() {
        return tag;
    }

    public int getTrackedCount() {
        return trackedCount;
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public JSONObject generateJSON() {
        JSONObject globalJSON = new JSONObject();
        JSONObject trackingJSON = new JSONObject();
        JSONObject customFieldsJSON;

        trackingJSON.put("tracking_number", this.trackingNumber);
        if (this.slug != null) trackingJSON.put("slug", this.slug);
        if (this.title != null) trackingJSON.put("title", this.title);
        if (this.emails != null) {
            JSONArray emailsJSON = new JSONArray(this.emails);
            trackingJSON.put("emails", emailsJSON);
        }
        if (this.smses != null) {
            JSONArray smsesJSON = new JSONArray(this.smses);
            trackingJSON.put("smses", smsesJSON);
        }
        if (this.customerName != null) trackingJSON.put("customer_name", this.customerName);
        if (this.destinationCountryISO3 != null)
            trackingJSON.put("destination_country_iso3", this.destinationCountryISO3);
        if (this.orderID != null) trackingJSON.put("order_id", this.orderID);
        if (this.orderIDPath != null) trackingJSON.put("order_id_path", this.orderIDPath);
        if (this.customFields != null) {
            customFieldsJSON = new JSONObject();

            for (Map.Entry<String, String> entry : this.customFields.entrySet()) {
                customFieldsJSON.put(entry.getKey(), entry.getValue());
            }
            trackingJSON.put("custom_fields", customFieldsJSON);
        }
        globalJSON.put("tracking", trackingJSON);

        return globalJSON;
    }

    public JSONObject generatePutJSON() {
        JSONObject globalJSON = new JSONObject();
        JSONObject trackingJSON = new JSONObject();
        JSONObject customFieldsJSON;

        if (this.title != null) trackingJSON.put("title", this.title);
        if (this.emails != null) {
            JSONArray emailsJSON = new JSONArray(this.emails);
            trackingJSON.put("emails", emailsJSON);
        }
        if (this.smses != null) {
            JSONArray smsesJSON = new JSONArray(this.smses);
            trackingJSON.put("smses", smsesJSON);
        }
        if (this.customerName != null) trackingJSON.put("customer_name", this.customerName);
        if (this.orderID != null) trackingJSON.put("order_id", this.orderID);
        if (this.orderIDPath != null) trackingJSON.put("order_id_path", this.orderIDPath);
        if (this.customFields != null) {
            customFieldsJSON = new JSONObject();

            for (Map.Entry<String, String> entry : this.customFields.entrySet()) {
                customFieldsJSON.put(entry.getKey(), entry.getValue());
            }
            trackingJSON.put("custom_fields", customFieldsJSON);
        }
        globalJSON.put("tracking", trackingJSON);

        return globalJSON;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("tracking{");
        sb.append((trackingNumber==null)?"":"\n\tTrackingNumber="+trackingNumber);
        sb.append((slug==null)?"":"\n\tslug=" +slug);
        sb.append((emails==null)?"":"\n\temails="+emails);
        sb.append((smses==null)?"":"\n\tsmses="+smses);
        sb.append(title==null?"": "\n\ttitle="+title);
        sb.append(customerName==null?"":"\n\tcustomerName=" +customerName);
        sb.append(destinationCountryISO3==null?"":"\n\tdestinationCountryISO3='" + destinationCountryISO3);
        sb.append(orderID==null?"": "\n\torderID='" +orderID);
        sb.append(orderIDPath==null?"": "\n\torderIDPath='" + orderIDPath);
        sb.append(customFields==null?"": "\n\tcustomFields=" +customFields);
        sb.append(createdAt==null?"":"\n\tcreatedAt='" + createdAt);
        sb.append(updatedAt==null?"":"\n\tupdatedAt='" +  updatedAt);
        sb.append("\n\t" + "active=" + active);
        sb.append(expectedDelivery==null?"":"\n\texpectedDelivery='" + expectedDelivery);
        sb.append(originCountryISO3==null?"":"\n\toriginCountryISO3='" +originCountryISO3);
        sb.append("\n\t" + "shipmentPackageCount=" + shipmentPackageCount);
        sb.append(shipmentType==null?"": "\n\tshipmentType='" +shipmentType);
        sb.append(signedBy==null?"":"\n\tsignedBy='" + signedBy);
        sb.append(source==null?"":  "\n\tsource='" +source);
        sb.append((tag==null)?"":"\n\ttag='" +tag );
        sb.append("\n\t" + "trackedCount=" + trackedCount);
        sb.append((checkpoints==null)?"": "\n\tcheckpoints=" +checkpoints);
        sb.append("\n}");
        return sb.toString();
    }
}
