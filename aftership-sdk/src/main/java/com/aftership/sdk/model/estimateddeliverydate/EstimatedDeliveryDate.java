package com.aftership.sdk.model.estimateddeliverydate;

import lombok.Data;

/**
 * aftership_estimated_delivery_date information.
 */
@Data
public class EstimatedDeliveryDate {
  /**
   * AfterShip's unique code of courier.Please refer to https://track.aftership.com/couriers/download.
   */
  private String slug;

  /**
   * Shipping and delivery options provided by the carrier.
   */
  private String serviceTypeName;

  /**
   * The location from where the package is picked up by the carrier to be delivered to the final destination.
   */
  private Address originAddress;

  /**
   * The final destination of the customer where the delivery will be made.
   */
  private Address destinationAddress;

  /**
   * AfterShip uses this object to calculate the total weight of the order.
   */
  private Weight weight;

  /**
   * The number of packages.
   */
  private Integer packageCount;

  /**
   * The local pickup time of the package.
   */
  private String pickupTime;

  /**
   * Either `pickup_time` or `estimated_pickup` is required.
   */
  private EstimatedPickup estimatedPickup;

  /**
   * The estimated arrival date of the shipment.
   *
   * <p>String
   */
  private String estimatedDeliveryDate;

  /**
   * The reliability of the estimated delivery date based on the trend of the transit time
   * for the similar delivery route and the carrier's delivery performance
   * range from 0.0 to 1.0 (Beta feature).
   *
   *
   * <p>Float
   */
  private Float confidenceScore;

  /**
   * Earliest estimated delivery date of the shipment.
   *
   * <p>String
   */
  private String estimatedDeliveryDateMin;

  /**
   * Latest estimated delivery date of the shipment.
   *
   * <p>String
   */
  private String estimatedDeliveryDateMax;
}
