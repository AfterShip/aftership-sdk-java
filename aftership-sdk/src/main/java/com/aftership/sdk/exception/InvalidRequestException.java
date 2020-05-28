package com.aftership.sdk.exception;

import java.util.Map;

/** Exception for request api interface */
public class InvalidRequestException extends AftershipException {

  /**
   * Constructor
   * @param type Type of error
   * @param message Message of error
   * @param data Debug information of error
   */
  public InvalidRequestException(String type, String message, Map<String, Object> data) {
    super(type, message, data);
  }
}
