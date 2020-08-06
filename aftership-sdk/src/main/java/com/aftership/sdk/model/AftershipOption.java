package com.aftership.sdk.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/** Optional parameters for API request */
@Data
@NoArgsConstructor
public class AftershipOption {

  /**
   * Global default timeout
   */
  public static final long DEFAULT_TIMEOUT = 20 * 1000L;

  /** Url of endpoint */
  private String endpoint;

  /** Prefix of UserAgent */
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

}
