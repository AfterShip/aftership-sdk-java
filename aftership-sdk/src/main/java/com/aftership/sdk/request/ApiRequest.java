package com.aftership.sdk.request;

import java.util.Map;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.model.AftershipResponse;

/** Request for Aftership's API interface */
public interface ApiRequest {

  /**
   * make a request of api
   *
   * @param method Method of http request
   * @param path path of request url
   * @param queryParams query params
   * @param requestData request data of body
   * @param responseType Type of response
   * @param <T> Class of request Data
   * @param <R> Class of response type
   * @return AftershipResponse
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  <T, R> AftershipResponse<R> makeRequest(
      HttpMethod method,
      String path,
      Map<String, String> queryParams,
      T requestData,
      Class<R> responseType)
      throws RequestException, ApiException;
}
