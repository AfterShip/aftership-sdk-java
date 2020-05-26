package com.aftership.sdk.model.tracking;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.aftership.sdk.utils.DateUtils;
import com.aftership.sdk.utils.StrUtils;
import lombok.Data;

/** GetTrackingListParams represents the set of params for get getTrackingList API */
@Data
public class MultiTrackingsParams {
  /** Page to show. (Default: 1) */
  private Integer page;
  /** Number of trackings each page contain. (Default: 100, Max: 200) */
  private Integer limit;
  /**
   * Search the content of the tracking record fields: tracking_number, title, order_id,
   * customer_name, custom_fields, order_id, emails, smses
   */
  private String keyword;
  /** Unique courier code Use comma for multiple values. (Example: dhl,ups,usps) */
  private String slug;
  /**
   * Total delivery time in days. - Difference of 1st checkpoint time and delivered time for
   * delivered shipments - Difference of 1st checkpoint time and current time for non-delivered
   * shipments Value as 0 for pending shipments or delivered shipment with only one checkpoint.
   */
  private Integer deliveryTime;
  /**
   * Origin country of trackings. Use ISO Alpha-3 (three letters). Use comma for multiple values.
   * (Example: USA,HKG)
   */
  private String origin;
  /**
   * Destination country of trackings. Use ISO Alpha-3 (three letters). Use comma for multiple
   * values. (Example: USA,HKG)
   */
  private String destination;
  /**
   * Current status of tracking. Values include Pending, InfoReceived, InTransit, OutForDelivery,
   * AttemptFail, Delivered, AvailableForPickup, Exception, Expired(See tag definition)
   */
  private String tag;
  /**
   * Start date and time of trackings created. AfterShip only stores data of 90 days. (Defaults: 30
   * days ago, Example: 2013-03-15T16:41:56+08:00)
   */
  private Date createdAtMin;
  /** End date and time of trackings created. (Defaults: now, Example: 2013-04-15T16:41:56+08:00) */
  private Date createdAtMax;
  /**
   * List of fields to include in the response. Use comma for multiple values. Fields to include:
   * title, order_id, tag, checkpoints, checkpoint_time, message, country_name Defaults: none,
   * Example: title,order_id
   */
  private String fields;
  /**
   * Default: '' / Example: 'en' Support Chinese to English translation for china-ems and china-post
   * only
   */
  private String lang;

  /**
   * Generate a Map dictionary.
   *
   * @return StringStringMap
   */
  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("page", this.getPage() != null ? this.getPage().toString() : StrUtils.EMPTY);
    map.put("limit", this.getLimit() != null ? this.getLimit().toString() : StrUtils.EMPTY);
    map.put("keyword", this.getKeyword());
    map.put("slug", this.getSlug());
    map.put(
        "delivery_time",
        this.getDeliveryTime() != null ? this.getDeliveryTime().toString() : StrUtils.EMPTY);
    map.put("origin", this.getOrigin());
    map.put("destination", this.getDestination());
    map.put("tag", this.getTag());
    // Example: 2013-03-15T16:41:56+08:00
    map.put("created_at_min", DateUtils.format(DateUtils.FORMAT_WITH_X, this.getCreatedAtMin()));
    // Example: 2013-04-15T16:41:56+08:00
    map.put("created_at_max", DateUtils.format(DateUtils.FORMAT_WITH_X, this.getCreatedAtMax()));
    map.put("fields", this.getFields());
    map.put("lang", this.getLang());
    return map;
  }

  /** field of 'List of fields to include in the response' */
  public static class FieldsKind {
    public static final String TRACKING_POSTAL_CODE = "tracking_postal_code";
    public static final String TRACKING_SHIP_DATE = "tracking_ship_date";
    public static final String TRACKING_ACCOUNT_NUMBER = "tracking_account_number";
    public static final String TRACKING_KEY = "tracking_key";
    public static final String TRACKING_ORIGIN_COUNTRY = "tracking_origin_country";
    public static final String TRACKING_DESTINATION_COUNTRY = "tracking_destination_country";
    public static final String TRACKING_STATE = "tracking_state";
    public static final String TITLE = "title";
    public static final String ORDER_ID = "order_id";
    public static final String TAG = "tag";
    public static final String CHECKPOINTS = "checkpoints";
    public static final String CHECKPOINT_TIME = "checkpoint_time";
    public static final String MESSAGE = "message";
    public static final String COUNTRY_NAME = "country_name";

    /**
     * Combine to Comma-delimited strings
     *
     * @param fields Array
     * @return Comma-delimited strings
     */
    public static String combine(String... fields) {
      if (fields == null) {
        return StrUtils.EMPTY;
      }
      return String.join(",", fields);
    }
  }

  /** lang for 'Support Chinese to English translation' */
  public static class LangKind {
    public static final String CHINA_EMS = "china-ems";
    public static final String CHINA_POST = "china-post";
  }
}
