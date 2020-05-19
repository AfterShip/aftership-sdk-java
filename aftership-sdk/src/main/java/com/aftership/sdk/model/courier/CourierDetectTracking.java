package com.aftership.sdk.model.courier;

import java.util.List;
import lombok.Data;

/** The tracking object in couriers detect request */
@Data
public class CourierDetectTracking {
  /**
   * Tracking number of a shipment. (Required)
   *
   * <p>String
   */
  private String trackingNumber;
  /**
   * The postal code of receiver's address. Required by some couriers, such asdeutsch-post
   *
   * <p>String
   */
  private String trackingPostalCode;
  /**
   * Shipping date inYYYYMMDDformat. Required by some couriers, such asdeutsch-post
   *
   * <p>String
   */
  private String trackingShipDate;
  /**
   * Account number of the shipper for a specific courier. Required by some couriers, such
   * asdynamic-logistics
   *
   * <p>String
   */
  private String trackingAccountNumber;
  /**
   * Key of the shipment for a specific courier. Required by some couriers, such assic-teliway
   *
   * <p>String
   */
  private String trackingKey;
  /**
   * Destination Country of the shipment for a specific courier. Required by some couriers, such
   * aspostnl-3s
   *
   * <p>String
   */
  private String trackingDestinationCountry;
  /**
   * If not specified, Aftership will automatically detect the courier based on the tracking number
   * format and your selected couriers. Use array or comma separated to input a list of couriers for
   * auto detect.
   *
   * <p><b>Array or String</b>
   */
  private List<String> slug;
}
