package com.aftership.sdk.error;

/**
 * Exception for calling the API interface
 *
 * @author chenjunbiao
 */
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
