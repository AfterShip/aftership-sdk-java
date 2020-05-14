package com.aftership.sdk.model.tracking;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.aftership.sdk.lib.DateUtil;
import com.aftership.sdk.lib.StrUtil;
import lombok.Data;

/**
 * GetTrackingListParams represents the set of params for get getTrackingList API
 *
 * @author chenjunbiao
 */
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
   * @return Map<String, String>
   */
  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("page", this.getPage() != null ? this.getPage().toString() : StrUtil.EMPTY);
    map.put("limit", this.getLimit() != null ? this.getLimit().toString() : StrUtil.EMPTY);
    map.put("keyword", this.getKeyword());
    map.put("slug", this.getSlug());
    map.put(
        "delivery_time",
        this.getDeliveryTime() != null ? this.getDeliveryTime().toString() : StrUtil.EMPTY);
    map.put("origin", this.getOrigin());
    map.put("destination", this.getDestination());
    map.put("tag", this.getTag());
    // Example: 2013-03-15T16:41:56+08:00
    map.put("created_at_min", DateUtil.format(DateUtil.FORMAT_WITH_X, this.getCreatedAtMin()));
    // Example: 2013-04-15T16:41:56+08:00
    map.put("created_at_max", DateUtil.format(DateUtil.FORMAT_WITH_X, this.getCreatedAtMax()));
    map.put("fields", this.getFields());
    map.put("lang", this.getLang());
    return map;
  }
}
