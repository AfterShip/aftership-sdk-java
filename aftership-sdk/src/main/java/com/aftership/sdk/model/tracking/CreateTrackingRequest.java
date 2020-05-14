package com.aftership.sdk.model.tracking;

import lombok.Data;

/**
 * Request body for CreateTracking
 *
 * @author chenjunbiao
 */
@Data
public class CreateTrackingRequest {
  /** NewTracking Object */
  private NewTracking tracking;
}
