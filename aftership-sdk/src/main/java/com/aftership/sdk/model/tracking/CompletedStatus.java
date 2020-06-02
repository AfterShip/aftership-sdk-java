package com.aftership.sdk.model.tracking;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Request body for MarkAsCompleted */
@Data
@AllArgsConstructor
public class CompletedStatus {

  /**
   * One of "DELIVERED", "LOST" or "RETURNED_TO_SENDER".
   *
   * <ul>
   *   <li>Mark the tracking as completed with "DELIVERED". The tag of the tracking will be updated
   *       to Delivered and the subtag will be updated to Delivered_001.
   *   <li>Mark the tracking as completed with "LOST". The tag of the tracking will be updated to
   *       Exception and the subtag will be updated to Exception_013.
   *   <li>Mark the tracking as completed with "RETURNED_TO_SENDER". The tag of the tracking will be
   *       updated to Exception and the subtag will be updated to Exception_011.
   * </ul>
   */
  private String reason;

  /** Reason of MarkAsCompleted */
  public static class ReasonKind {
    /**
     * Mark the tracking as completed with "DELIVERED". The tag of the tracking will be updated to
     * Delivered and the subtag will be updated to Delivered_001.
     */
    public static final String DELIVERED = "DELIVERED";
    /**
     * Mark the tracking as completed with "LOST". The tag of the tracking will be updated to
     * Exception and the subtag will be updated to Exception_013.
     */
    public static final String LOST = "LOST";
    /**
     * Mark the tracking as completed with "RETURNED_TO_SENDER". The tag of the tracking will be
     * updated to Exception and the subtag will be updated to Exception_011.
     */
    public static final String RETURNED_TO_SENDER = "RETURNED_TO_SENDER";
  }
}
