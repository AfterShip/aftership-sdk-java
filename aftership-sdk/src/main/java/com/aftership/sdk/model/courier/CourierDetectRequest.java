package com.aftership.sdk.model.courier;

import lombok.Value;

/** The request object of couriers detect */
@Value
public class CourierDetectRequest {
  /** Tracking Object. */
  CourierDetectTracking tracking;

  /**
   * CourierDetectRequest constructor
   *
   * @param tracking tracking object, the tracking_number field is required.
   */
  public CourierDetectRequest(CourierDetectTracking tracking) {
    this.tracking = tracking;
  }
}
