package com.aftership.sdk.exception;

import java.util.Map;
import com.aftership.sdk.utils.MapUtils;

/** Exception for request api interface */
public class RequestException extends AftershipException {

  /**
   * Constructor
   *
   * @param type Type of error
   * @param message Message of error
   * @param data Debug information of error
   */
  public RequestException(String type, String message, Map<String, Object> data) {
    super(type, message, data);
  }

  @SafeVarargs
  public RequestException(String type, String message, Map.Entry<String, Object>... data) {
    super(type, message, MapUtils.toMap(data));
  }

  @SafeVarargs
  public RequestException(String type, Throwable t, Map.Entry<String, Object>... data){
    super(type, t, MapUtils.toMap(data));
  }

}
