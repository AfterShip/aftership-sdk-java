package com.aftership.sdk.model.estimateddeliverydate;


import lombok.Data;

@Data
public class OrderProcessingTime {
  /**
   * Processing time of an order, from being placed to being picked up.
   * Only support day as value now. AfterShip will set day as the default value.
   */
  private String unit;

  // Processing time of an order, from being placed to being picked up.
  // AfterShip will set 0 as the default value.
  private Integer value;

}
