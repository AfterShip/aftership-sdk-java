package com.aftership.sdk.model.courier;

import java.util.List;

import lombok.Data;

/**
 * The tracking object in couriers detect request
 */
@Data
public class CourierDetectTracking {
  /**
   * Tracking number of a shipment. (Required)
   *
   * <p>String
   */
  private String trackingNumber;

  /**
   * If not specified, Aftership will automatically detect the courier based on the tracking number
   * format and your selected couriers. Use array or comma separated to input a list of couriers for
   * auto detect.
   *
   * <p><b>Array or String</b>
   */
  private List<String> slug;

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
   * Origin Country of the shipment for a specific courier. Required by some couriers.
   *
   * <p>String
   */
  private String trackingOriginCountry;

  /**
   * Destination Country of the shipment for a specific courier. Required by some couriers, such
   * aspostnl-3s
   *
   * <p>String
   */
  private String trackingDestinationCountry;

  /**
   * State of the destination shipping address of the shipment. Required by some couriers.
   *
   * <p>String
   */
  private String trackingState;

  /**
   * Slug group is a group of slugs which belong to same courier. For example, when you inpit "fedex-group" as slug_group, AfterShip will detect the tracking with "fedex-uk", "fedex-fims", and other slugs which belong to "fedex". It cannot be used with slug at the same time.
   *
   * <p>String
   */
  private String slugGroup;

  /**
   * Enter ISO Alpha-3 (three letters) to specify the origin of the shipment (e.g. USA for United States).
   *
   * <p>String
   */
  private String originCountryIso3;

  /**
   * Enter ISO Alpha-3 (three letters) to specify the destination of the shipment (e.g. USA for United States).
   *
   * <p>String
   */
  private String destinationCountryIso3;
}
