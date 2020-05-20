package com.aftership.sdk.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Request body for CreateTracking */
@Data
@AllArgsConstructor
public class CreateTrackingRequest {
  /** NewTracking Object */
  private NewTracking tracking;
}
