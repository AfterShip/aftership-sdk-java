package com.aftership.sdk.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlugTrackingNumber {
  /** Unique code of courier */
  private String slug;
  /** Tracking number of a shipment. */
  private String trackingNumber;

  /** OptionalParams for SingleTracking */
  private SingleTrackingOptionalParams optionalParams;

  public SlugTrackingNumber(String slug, String trackingNumber) {
    this.slug = slug;
    this.trackingNumber = trackingNumber;
  }
}
