package com.aftership.sdk.auth;

/** Types of authentications */
public enum AuthenticationType {

  /** AES authentication type */
  AES("AES");

  private String type;

  AuthenticationType(String t) {
    this.type = t;
  }

  /**
   * get value of AuthenticationType
   * @return type
   */
  public String getType() {
    return type;
  }
}
