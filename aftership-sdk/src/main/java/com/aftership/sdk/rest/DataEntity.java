package com.aftership.sdk.rest;

import com.aftership.sdk.error.AftershipError;

/**
 * Data Entity's wrapper.
 *
 * @author chenjunbiao
 * @param <T> Class
 */
public interface DataEntity<T> {

  /**
   * Data of response
   * @return
   */
  T getData();

  /**
   * Error of request
   * @return
   */
  AftershipError getError();

  /**
   * Is there an error in the response
   * @return true/false
   */
  default boolean hasError() {
    return getError() != null;
  }
}
