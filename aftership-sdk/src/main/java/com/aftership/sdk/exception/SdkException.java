package com.aftership.sdk.exception;

import com.aftership.sdk.error.ErrorType;

/** Exception for constructed parameter */
public class SdkException extends AftershipException {

  /**
   * Constructor
   * @param type Type of error
   * @param message Message of error
   */
  public SdkException(ErrorType type, String message) {
    super(type.getName(), message);
  }
}
