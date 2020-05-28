package com.aftership.sdk.exception;

import java.util.Map;

/** Exception for API Server */
public class ApiException extends AftershipException {

  /**
   * Constructor
   * @param type Type of error
   * @param message Message of error
   * @param code Coding of error
   * @param data Debug information of error
   */
  public ApiException(String type, String message, Integer code, Map<String, Object> data) {
    super(type, message, code, data);
  }
}
