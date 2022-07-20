package com.aftership.sdk.model.estimateddeliverydate;

import lombok.Data;

import java.util.List;

/**
 * aftership_estimated_delivery_date list.
 */
@Data
public class EstimatedDeliveryDateList {
  /**
   * a list of EstimatedDeliveryDate
   */
  private List<EstimatedDeliveryDate> estimatedDeliveryDates;
}
