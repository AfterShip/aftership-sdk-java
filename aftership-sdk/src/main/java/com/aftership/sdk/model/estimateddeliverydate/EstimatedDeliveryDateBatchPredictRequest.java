package com.aftership.sdk.model.estimateddeliverydate;

import lombok.Value;

import java.util.List;

@Value
public class EstimatedDeliveryDateBatchPredictRequest {
  /**
   * a list of EstimatedDeliveryDate
   */
  List<EstimatedDeliveryDate> estimatedDeliveryDates;

  public EstimatedDeliveryDateBatchPredictRequest(List<EstimatedDeliveryDate> estimatedDeliveryDates) {
    this.estimatedDeliveryDates = estimatedDeliveryDates;
  }
}
