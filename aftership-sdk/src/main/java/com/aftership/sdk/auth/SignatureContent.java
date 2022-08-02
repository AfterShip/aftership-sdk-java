package com.aftership.sdk.auth;

import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.util.Map;

@Data
@Builder
public class SignatureContent {
  /**
   * method of http request
   */
  private String method;
  /**
   * request data of body
   */
  private String body;
  /**
   * Content type string.If the request body is empty, set content_type to an empty string.
   */
  private String contentType;
  /**
   * UTC time in RFC 1123 format.Kindly note that the calculated signature is only valid for 3 minutes before or after the datetime indicated in this key.
   */
  private String date;
  /**
   * request url
   */
  private String urlStr;
  /**
   * request headers
   */
  private Map<String, String> headers;
}
