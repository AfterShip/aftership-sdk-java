package com.aftership.sdk.exception;

import java.util.Map;
import com.aftership.sdk.model.Meta;
import com.aftership.sdk.utils.MapUtils;

/** Exception for API Server */
public class ApiException extends AftershipException {

  /**
   * Constructor
   *
   * @param type Type of error
   * @param message Message of error
   * @param code Coding of error
   * @param data Debug information of error
   */
  public ApiException(String type, String message, Integer code, Map<String, Object> data) {
    super(type, message, code, data);
  }

  /**
   * Constructor
   *
   * @param meta The meta key is used to communicate extra information about the response to the
   *     developer
   * @param data Debug information of error
   */
  @SafeVarargs
  public ApiException(Meta meta, Map.Entry<String, Object>... data) {
    super(meta.getType(), meta.getMessage(), meta.getCode(), MapUtils.toMap(data));
  }
}
