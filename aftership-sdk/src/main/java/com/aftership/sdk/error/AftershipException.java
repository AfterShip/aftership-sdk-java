package com.aftership.sdk.error;

/** Exception for calling the API interface */
public class AftershipException extends RuntimeException {

  /**
   * Constructor
   *
   * @param message Message of Exception
   */
  public AftershipException(String message) {
    super(message);
  }
}
