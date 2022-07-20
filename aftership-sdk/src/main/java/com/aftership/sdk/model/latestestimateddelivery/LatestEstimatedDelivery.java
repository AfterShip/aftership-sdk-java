package com.aftership.sdk.model.latestestimateddelivery;

import lombok.Data;

/**
 * latest_estimated_delivery information
 */
@Data
public class LatestEstimatedDelivery {
  /**
   * The format of the EDD. Either a single date or a date range.
   */
  private String type;

  /**
   * The source of the EDD. Either the carrier, AfterShip AI, or based on your custom EDD settings.
   */
  private String source;

  /**
   * The latest EDD time.
   */
  private String datetime;

  /**
   * For a date range EDD format, the date and time for the lower end of the range.
   */
  private String datetimeMin;

  /**
   * For a date range EDD format, the date and time for the upper end of the range.
   */
  private String datetimeMax;

}
