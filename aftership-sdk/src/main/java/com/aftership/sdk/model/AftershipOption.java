package com.aftership.sdk.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/** Optional parameters for API request */
@Data
@NoArgsConstructor
public class AftershipOption {
  /** Url of endpoint */
  private String endpoint;
  /** Prefix of UserAgent */
  private String userAgentPrefix;

  /**
   * Constructor
   *
   * @param endpoint endpoint url of api
   */
  public AftershipOption(String endpoint) {
    this.endpoint = endpoint;
  }
}
