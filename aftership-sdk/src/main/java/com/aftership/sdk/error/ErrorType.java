package com.aftership.sdk.error;

import com.aftership.sdk.utils.StrUtils;

/** Types of errors */
public enum ErrorType {

  /** Constructor Error */
  ConstructorError("ConstructorError"),

  /** Handler Error */
  HandlerError("HandlerError");

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

  public static ErrorType get(String name) {
    if (StrUtils.isNotBlank(name)) {
      for (ErrorType r : ErrorType.values()) {
        if (r.name.equals(name)) {
          return r;
        }
      }
    }
    return null;
  }
}
