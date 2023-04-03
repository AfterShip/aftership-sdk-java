package com.aftership.sdk.model;

import com.aftership.sdk.request.retry.RetryCondition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Optional parameters for retry
 */
@Data
@NoArgsConstructor
public class RetryOption {

  /**
   * The initial retry delay in milliseconds. The default value is DEFAULT_RETRY_DELAY.
   * Ensure that the value does not exceed retryMaxDelay.
   */
  private long retryDelay;

  /**
   * The maximum retry delay in milliseconds. The default value is DEFAULT_RETRY_MAX_DELAY.
   * Ensure that the value does not exceed the timeout you set.
   */
  private long retryMaxDelay;

  /**
   * The number of retries. To use the retry function, this field must be set to a value greater than 0.
   */
  private int retryCount;

  /**
   * The list of retry conditions.
   */
  private List<RetryCondition> retryConditions;

}
