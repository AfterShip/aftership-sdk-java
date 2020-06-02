package com.aftership.sdk.request;

/** Method of http request */
public enum HttpMethod {
  /** POST Method of http request */
  POST("POST"),
  /** PUT Method of http request */
  PUT("PUT"),
  /** PATCH Method of http request */
  PATCH("PATCH"),
  /** GET Method of http request */
  GET("GET"),
  /** DELETE Method of http request */
  DELETE("DELETE");

  private String name;

  /**
   * Constructor
   *
   * @param name of method
   */
  HttpMethod(String name) {
    this.name = name;
  }

  /**
   * Name of Method
   *
   * @return name
   */
  public String getName() {
    return name;
  }
}
