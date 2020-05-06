package com.aftership.sdk.rest;


public interface ApiRequest {
   <T, R> ResponseEntity<R> makeRequest(RequestConfig requestConfig, T requestData, Class<R> responseType);
}
