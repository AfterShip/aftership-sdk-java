package com.aftership.sdk.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Basic configuration of the request */
@Data
@AllArgsConstructor
@Deprecated
public class RequestConfig {
  /** HttpMethod of request */
  private HttpMethod method;
  /** path of request url */
  private String path;
}
