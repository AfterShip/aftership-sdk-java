package com.aftership.sdk.model;

import lombok.Data;

/**
 * Optional parameters for API request
 *
 * @author chenjunbiao
 */
@Data
public class AftershipOption {
  /** Url of endpoint */
  private String endpoint;
  /** Prefix of UserAgent */
  private String userAgentPrefix;
}
