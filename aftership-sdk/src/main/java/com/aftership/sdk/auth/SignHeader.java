package com.aftership.sdk.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignHeader {
    /**
   * Signature key in request header
   */
  private String header;
  /**
   * Encrypted signature
   */
  private String signature;
}
