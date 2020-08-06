package com.aftership.sdk.model;

import lombok.Data;

/** The meta key is used to communicate extra information about the response to the developer. */
@Data
public class Meta {
  /** Coding of error */
  private Integer code;
  /** Message of error */
  private String message;
  /** Type of error */
  private String type;
}
