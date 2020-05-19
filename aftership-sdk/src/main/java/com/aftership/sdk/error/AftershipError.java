package com.aftership.sdk.error;

import java.util.*;
import com.aftership.sdk.lib.StrUtil;
import com.aftership.sdk.model.Meta;
import com.aftership.sdk.rest.BodyParser;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Error description for calling the API interface */
@Data
@NoArgsConstructor
public class AftershipError {
  /** Type of error */
  private String type = StrUtil.EMPTY;
  /** Message of error */
  private String message = StrUtil.EMPTY;
  /** Coding of error */
  private Integer code = null;
  /** Debug information of error */
  private Map<String, Object> data;

  /**
   * Is it a processing error on the SDK side?
   *
   * @return true is sdk error.
   */
  public boolean isSdkError() {
    return ErrorType.get(getType()) != null;
  }

  /**
   * Is it a processing error on the API side?
   *
   * @return true is API return error.
   */
  public boolean isApiError() {
    return !isSdkError();
  }

  /**
   * Create a AftershipError
   *
   * @param jsonBody Json string of response
   * @param data Debug information of error
   * @return AftershipError
   */
  @SafeVarargs
  public static AftershipError make(String jsonBody, Map.Entry<String, Object>... data) {
    return make(BodyParser.processMeta(jsonBody), data);
  }

  /**
   * Create a AftershipError
   *
   * @param meta The meta key is used to communicate extra information about the response to the
   *     developer
   * @param data Debug information of error
   * @return AftershipError
   */
  @SafeVarargs
  public static AftershipError make(Meta meta, Map.Entry<String, Object>... data) {
    if (meta != null) {
      return make(meta.getType(), meta.getMessage(), meta.getCode(), data);
    }
    return make(ErrorType.HandlerError, ErrorMessage.HANDLER_NULL_META);
  }

  /**
   * Create a AftershipError
   *
   * @param errorType Type of error
   * @param t Throwable
   * @param data Debug information of error
   * @return AftershipError
   */
  @SafeVarargs
  public static AftershipError make(
      ErrorType errorType, Throwable t, Map.Entry<String, Object>... data) {
    // t.printStackTrace();
    return make(errorType.getName(), t.getMessage(), data);
  }

  /**
   * Create a AftershipError
   *
   * @param errorType Type of error
   * @param message Message of error
   * @param data Debug information of error
   * @return AftershipError
   */
  @SafeVarargs
  public static AftershipError make(
      ErrorType errorType, String message, Map.Entry<String, Object>... data) {
    return make(errorType.getName(), message, data);
  }

  /**
   * Create a AftershipError
   *
   * @param type Type of error
   * @param message Message of error
   * @param data Debug information of error
   * @return AftershipError
   */
  @SafeVarargs
  public static AftershipError make(
      String type, String message, Map.Entry<String, Object>... data) {
    return make(type, message, null, data);
  }

  /**
   * Create a AftershipError
   *
   * @param type Type of error
   * @param message Message of error
   * @param code Code of error
   * @param data Debug information of error
   * @return AftershipError
   */
  @SafeVarargs
  public static AftershipError make(
      String type, String message, Integer code, Map.Entry<String, Object>... data) {
    AftershipError error = new AftershipError();
    if (StrUtil.isNotBlank(type)) {
      error.setType(type);
    }
    if (StrUtil.isNotBlank(message)) {
      error.setMessage(message);
    }
    if (code != null) {
      error.setCode(code);
    }

    if (data != null && data.length > 0) {
      Map<String, Object> map = new HashMap<>();
      for (Map.Entry<String, Object> item : data) {
        if (!map.containsKey(item.getKey())) {
          map.put(item.getKey(), item.getValue());
        }
      }
      error.setData(map);
    } else {
      error.setData(new HashMap<>());
    }

    return error;
  }
}
