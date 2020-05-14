package com.aftership.sdk.model;

import lombok.Data;

/**
 * Encapsulation of the response after requesting Aftership's API interface
 *
 * @author chenjunbiao
 * @param <T> Some Class
 */
@Data
public class AftershipResponse<T> {
  private Meta meta;
  private T data;

  /** Default constructor */
  public AftershipResponse() {}

  /**
   * Constructor
   *
   * @param data Object
   * @param meta Object of Meta
   */
  public AftershipResponse(T data, Meta meta) {
    this.data = data;
    this.meta = meta;
  }
}
