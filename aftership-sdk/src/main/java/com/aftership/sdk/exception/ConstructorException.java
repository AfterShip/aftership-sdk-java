package com.aftership.sdk.exception;

/** Exception for constructed parameter */
public class ConstructorException extends AftershipException {

  /**
   * Constructor
   * @param type Type of error
   * @param message Message of error
   */
  public ConstructorException(String type, String message) {
    super(type, message);
  }
}
