package com.aftership.sdk.exception;

/** Exception for constructed parameter */
public class SdkException extends AftershipException {

  /**
   * Constructor
   * @param type Type of error
   * @param message Message of error
   */
  public SdkException(String type, String message) {
    super(type, message);
  }
}
