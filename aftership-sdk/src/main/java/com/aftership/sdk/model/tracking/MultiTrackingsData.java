package com.aftership.sdk.model.tracking;

import java.util.List;
import lombok.Data;

/**
 * MultiTrackingsData is a model for data part of the multiple trackings API responses
 *
 * @author chenjunbiao
 */
@Data
public class MultiTrackingsData {
  /** Number of trackings each page contain. (Default: 100) */
  private Integer limit;
  /** Total number of matched trackings, max. number is 10,000 */
  private Integer count;
  /** Page to show. (Default: 1) */
  private Integer page;
  /** Array of Hash describes the tracking information. */
  private List<Tracking> trackings;
}
