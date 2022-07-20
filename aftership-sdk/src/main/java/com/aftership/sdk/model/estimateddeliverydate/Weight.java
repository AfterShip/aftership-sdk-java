package com.aftership.sdk.model.estimateddeliverydate;

import lombok.Data;

@Data
public class Weight {
  // The weight unit of the package.
  private String unit;

  // The weight of the shipment.
  private Integer value;
}