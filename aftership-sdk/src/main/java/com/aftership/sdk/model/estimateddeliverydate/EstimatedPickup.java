package com.aftership.sdk.model.estimateddeliverydate;

import lombok.Data;

import java.util.List;

@Data
public class EstimatedPickup {
  // The local order time of the package.
  private String orderTime;

  // Order cut off time. AfterShip will set 18:00:00 as the default value.
  private String orderCutoffTime;

  // Operating days in a week. Number refers to the weekday.
  // E.g., [1,2,3,4,5] means operating days are from Monday to Friday.
  // AfterShip will set [1,2,3,4,5] as the default value.
  private List<Integer> businessDays;

  private OrderProcessingTime orderProcessingTime;

  // The local pickup time of the package.
  private String pickupTime;
}

