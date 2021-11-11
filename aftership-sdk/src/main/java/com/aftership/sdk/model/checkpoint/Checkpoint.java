package com.aftership.sdk.model.checkpoint;

import java.util.Date;
import java.util.List;
import lombok.Data;

/** checkpoint information. */
@Data
public class Checkpoint {
  /**
   * Date and time of the tracking created.
   *
   * <p>DateTime
   */
  private Date createdAt;

  /**
   * The unique code of courier for this checkpoint message. Get courier slug here
   *
   * @link https://docs.aftership.com/api/4/couriers
   *     <p>String
   */
  private String slug;

  /**
   * Date and time of the checkpoint, provided by courier. Value may be: YYYY-MM-DD,
   * YYYY-MM-DDTHH:MM:SS, or YYYY-MM-DDTHH:MM:SS+TIMEZONE
   *
   * <p>String
   */
  private String checkpointTime;

  /**
   * Location info provided by carrier (if any)
   *
   * <p>String
   */
  private String location;

  /**
   * City info provided by carrier (if any)
   *
   * <p>String
   */
  private String city;

  /**
   * State info provided by carrier (if any)
   *
   * <p>String
   */
  private String state;

  /**
   * Location info (if any)
   *
   * <p>String
   */
  private String zip;

  /**
   * Deprecated as of March 2013
   *
   * <p>Array
   */
  private List<String> coordinates;

  /**
   * Country ISO Alpha-3 (three letters) of the checkpoint
   *
   * <p>String
   */
  private String countryIso3;

  /**
   * Country name of the checkpoint, may also contain other location info.
   *
   * <p>String
   */
  private String countryName;

  /**
   * Checkpoint message
   *
   * <p>String
   */
  private String message;

  /**
   * Current status of checkpoint. Values include Pending InfoReceived InTransit OutForDelivery
   * AttemptFail Delivered AvailableForPickup Exception Expired (See tag definition)
   *
   * @link https://docs.aftership.com/api/4/delivery-status
   *     <p>String
   */
  private String tag;

  /**
   * Current subtag of checkpoint. (See subtag definition)
   *
   * @link https://help.aftership.com/hc/en-us/articles/360007823253
   *     <p>String
   */
  private String subtag;

  /**
   * Normalized checkpoint message. (See subtag message definition)
   *
   * @link https://help.aftership.com/hc/en-us/articles/360007823253
   *     <p>String
   */
  private String subtagMessage;

  /**
   * Checkpoint status provided by courier (if any)
   *
   * <p>String
   */
  private String rawTag;
}
