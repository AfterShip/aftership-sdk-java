package com.aftership.sdk.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Single Tracking wrapper */
@Data
@AllArgsConstructor
public class SingleTracking {
  /** Tracking object */
  private Tracking tracking;
}
