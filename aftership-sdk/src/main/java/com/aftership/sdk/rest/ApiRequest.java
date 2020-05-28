package com.aftership.sdk.rest;

import java.util.Map;

/** Request for Aftership's API interface */
public interface ApiRequest {

//  /**
//   * make a request of api
//   *
//   * @param requestConfig Basic configuration of the request
//   * @param requestData Requested body data
//   * @param responseType Type of response
//   * @param <T> Class of request Data
//   * @param <R> Class of response type
//   * @return ResponseEntity
//   */
//  <T, R> ResponseEntity<R> makeRequest(
//      RequestConfig requestConfig, T requestData, Class<R> responseType);

  /**
   * make a request of api
   * @param method Method of http request
   * @param path path of request url
   * @param queryParams query params
   * @param requestData request data of body
   * @param responseType Type of response
   * @param <T> Class of request Data
   * @param <R> Class of response type
   * @return ResponseEntity
   */
  <T, R> ResponseEntity<R> makeRequest(
      HttpMethod method, String path, Map<String, String> queryParams, T requestData, Class<R> responseType);

}
