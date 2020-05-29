package com.aftership.sdk.request;

/**
 * Data Entity's wrapper.
 *
 * @param <T> Class
 */
@Deprecated
public interface DataEntity<T> {

  /**
   * Data of response
   *
   * @return object of T
   */
  T getData();

//  /**
//   * Error of request
//   *
//   * @return Object of AftershipError
//   */
//  AftershipError getError();

//  /**
//   * Is there an error in the response
//   *
//   * @return true/false
//   */
//  default boolean hasError() {
//    return getError() != null;
//  }
}
