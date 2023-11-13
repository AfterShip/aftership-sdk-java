package com.aftership.sdk.model.tracking;

import lombok.Data;

@Data
public class NextCourier {
  /**
   * Unique code of courier.
   */
  private String slug;

  /**
   * Tracking number.
   */
  private String trackingNumber;

  /**
   * Source of next couriers.
   */
  private String source;
}
