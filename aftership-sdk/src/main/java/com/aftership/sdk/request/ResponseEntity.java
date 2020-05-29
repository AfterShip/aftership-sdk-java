package com.aftership.sdk.request;

import com.aftership.sdk.model.AftershipResponse;
import lombok.Getter;

/**
 * Response Entity of API Request
 *
 * @param <T> Class of Response
 */
@Getter
@Deprecated
public class ResponseEntity<T> implements DataEntity<T> {
  /** Response of api request */
  private final AftershipResponse<T> response;
//  /** Error of api request */
//  private final AftershipError error;

  /**
   * Constructor
   *
   * @param response Response of api request
   */
  private ResponseEntity(AftershipResponse<T> response) {
    this.response = response;
  }

//  /**
//   * Get Error of api request
//   *
//   * @return AftershipError
//   */
//  @Override
//  public AftershipError getError() {
//    return this.error;
//  }

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

//  public static <T> ResponseEntity<T> makeError(AftershipError error) {
//    return new ResponseEntity<>(null, error);
//  }

  static <T> ResponseEntity<T> makeResponse(AftershipResponse<T> response) {
    return new ResponseEntity<>(response);
  }
}
