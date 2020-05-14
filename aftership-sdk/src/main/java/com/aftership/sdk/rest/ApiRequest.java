package com.aftership.sdk.rest;

/**
 * Request for Aftership's API interface
 *
 * @author chenjunbiao
 */
public interface ApiRequest {
  /**
   * make a request of api
   *
   * @param requestConfig Basic configuration of the request
   * @param requestData Requested body data
   * @param responseType Type of response
   * @param <T> Class of request Data
   * @param <R> Class of response type
   * @return ResponseEntity
   */
  <T, R> ResponseEntity<R> makeRequest(
      RequestConfig requestConfig, T requestData, Class<R> responseType);
}
