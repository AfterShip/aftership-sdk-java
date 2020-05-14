package com.aftership.sdk.error;

/**
 * Types of errors
 *
 * @author chenjunbiao
 */
public enum ErrorType {
  /** Constructor Error */
  ConstructorError("ConstructorError"),
  /** Handler Error */
  HandlerError("HandlerError"),
  /** Internal Error */
  InternalError("InternalError");

  /** name of ErrorType */
  private String name;

  /**
   * Constructor
   *
   * @param name get name of ErrorType
   */
  ErrorType(String name) {
    this.name = name;
  }

  /**
   * get name of ErrorType
   *
   * @return name
   */
  public String getName() {
    return name;
  }
}
