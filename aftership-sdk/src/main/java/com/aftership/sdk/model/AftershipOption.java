package com.aftership.sdk.model;

import com.aftership.sdk.request.retry.RetryCondition;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Optional parameters for API request
 */
@Data
@NoArgsConstructor
public class AftershipOption {

  /**
   * Global default timeout
   */
  public static final long DEFAULT_TIMEOUT = 20 * 1000L;

  /**
   * Default retry delay
   */
  public static final long DEFAULT_RETRY_DELAY = 500L;

  /**
   * Default maximum retry delay
   */
  public static final long DEFAULT_RETRY_MAX_DELAY = DEFAULT_TIMEOUT - (2 * 1000L);

  /**
   * Url of endpoint
   */
  private String endpoint;

  /**
   * Prefix of UserAgent
   */
  private String userAgentPrefix;

  /**
   * Sets the default timeout for complete calls. A value of 0 means no timeout, otherwise values
   * must be between 1 and [Integer.MAX_VALUE] when converted to milliseconds.
   */
  private long callTimeout;

  /**
   * Sets the default connect timeout for new connections. A value of 0 means no timeout,
   * otherwise values must be between 1 and [Integer.MAX_VALUE] when converted to milliseconds.
   */
  private long connectTimeout;

  /**
   * Sets the default read timeout for new connections. A value of 0 means no timeout, otherwise
   * values must be between 1 and [Integer.MAX_VALUE] when converted to milliseconds.
   */
  private long readTimeout;

  /**
   * Sets the default write timeout for new connections. A value of 0 means no timeout, otherwise
   * values must be between 1 and [Integer.MAX_VALUE] when converted to milliseconds.
   */
  private long writeTimeout;

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
