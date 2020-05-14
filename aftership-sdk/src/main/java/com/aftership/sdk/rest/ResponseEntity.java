package com.aftership.sdk.rest;

import com.aftership.sdk.error.AftershipError;
import com.aftership.sdk.model.AftershipResponse;
import lombok.Getter;

/**
 * Response Entity of API Request
 *
 * @author chenjunbiao
 * @param <T> Class of Response
 */
@Getter
public class ResponseEntity<T> implements DataEntity<T> {
  /** Response of api request */
  private final AftershipResponse<T> response;
  /** Error of api request */
  private final AftershipError error;

  /**
   * Constructor
   *
   * @param response Response of api request
   * @param error Error of api request
   */
  private ResponseEntity(AftershipResponse<T> response, AftershipError error) {
    this.response = response;
    this.error = error;
  }

  /**
   * Get Error of api request
   *
   * @return AftershipError
   */
  @Override
  public AftershipError getError() {
    return this.error;
  }

  /**
   * Get Response of api request
   *
   * @return Data of response
   */
  @Override
  public T getData() {
    if (this.response == null) {
      return null;
    }
    return this.response.getData();
  }

  public static <T> ResponseEntity<T> makeError(AftershipError error) {
    return new ResponseEntity<>(null, error);
  }

  static <T> ResponseEntity<T> makeResponse(AftershipResponse<T> response) {
    return new ResponseEntity<>(response, null);
  }
}
