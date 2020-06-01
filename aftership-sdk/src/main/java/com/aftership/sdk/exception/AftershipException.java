package com.aftership.sdk.exception;

import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import com.aftership.sdk.utils.JsonUtils;
import com.aftership.sdk.utils.StrUtils;
import lombok.Getter;

/** Exception for calling the API interface */
@Getter
public class AftershipException extends Exception {
  private static final int CODE_TOO_MANY_REQUESTS = 429;

  public static final String DEBUG_DATA_KEY_REQUEST_CONFIG = "requestConfig";
  public static final String DEBUG_DATA_KEY_REQUEST_HEADERS = "requestHeaders";
  public static final String DEBUG_DATA_KEY_REQUEST_DATA = "requestData";
  public static final String DEBUG_DATA_KEY_RESPONSE_BODY = "responseBody";

  /** Type of error */
  private String type;

  /** Message of error */
  private String message;

  /** Coding of error */
  private Integer code;

  /** Debug information of error */
  private Map<String, Object> data;

  /**
   * Constructor
   *
   * @param type Type of error
   * @param message Message of error
   */
  public AftershipException(String type, String message) {
    this(type, message, null);
  }

  public AftershipException(String type, Throwable cause, Map<String, Object> data) {
    this(type, null, null, cause, data);
  }

  /**
   * Constructor
   *
   * @param type Type of error
   * @param message Message of error
   * @param data Debug information of error
   */
  public AftershipException(String type, String message, Map<String, Object> data) {
    this(type, message, null, data);
  }

  /**
   * Constructor
   *
   * @param type Type of error
   * @param message Message of error
   * @param code Coding of error
   * @param data Debug information of error
   */
  public AftershipException(String type, String message, Integer code, Map<String, Object> data) {
    this(type, message, code, null, data);
  }

  /**
   * Constructor
   *
   * @param type Type of error
   * @param message Message of error
   * @param code Coding of error
   * @param cause Throwable
   * @param data Debug information of error
   */
  public AftershipException(
      String type, String message, Integer code, Throwable cause, Map<String, Object> data) {
    super(message, cause);
    this.type = type;
    this.message = message;
    this.code = code;
    this.data = data;
  }

  /**
   * Message of Exception
   *
   * @return String
   */
  @Override
  public String getMessage() {
    StringBuilder additionalInfo = new StringBuilder();

    if (StrUtils.isNotBlank(type)) {
      additionalInfo.append("; type: ").append(type);
    }
    if (code != null) {
      additionalInfo.append("; code: ").append(code);
    }

    if (data != null && data.size() > 0) {
      for (Map.Entry<String, Object> entry : data.entrySet()) {
        additionalInfo.append(
            MessageFormat.format("; DEBUG_DATA::{0}: {1}", entry.getKey(), entry.getValue()));
      }
    }

    return super.getMessage() + additionalInfo;
  }

  /**
   * Determine if the current exception is caused by "TooManyRequests", if so, please check the
   * RateLimit object
   *
   * @return boolean
   */
  public boolean isTooManyRequests() {
    if (this.getCode() != null && this.getCode() == CODE_TOO_MANY_REQUESTS) {
      return true;
    }
    return false;
  }

  /**
   * Is it a processing error on the API side?
   *
   * @return true is API return error.
   */
  public boolean isApiError() {
    return this.getCode() != null && this.getCode() > 0;
  }

  /** Print Message, Using System.out::print and Pretty message in Json format */
  public void printMessage() {
    printMessage(true);
  }

  /**
   * Print Message, Using System.out::print
   *
   * @param pretty is pretty message?
   */
  public void printMessage(boolean pretty) {
    printMessage(pretty, System.out::print);
  }

  /**
   * Print Message
   *
   * @param pretty boolean
   * @param consumer Log output method
   */
  public void printMessage(boolean pretty, Consumer<String> consumer) {
    consumer.accept(prettyMessage(pretty));
  }

  /**
   * Pretty message in Json format
   *
   * @return
   */
  public String prettyMessage() {
    return prettyMessage(true);
  }

  /**
   * Pretty message in Json format
   *
   * @param pretty is pretty message?
   * @return String
   */
  public String prettyMessage(boolean pretty) {
    Map<String, Object> map = new LinkedHashMap<>(4);
    map.put("type", getType());
    map.put("message", this.message);
    map.put("code", getCode());
    // TODO("need to optimize")
    map.put("data", getData().toString());
    return JsonUtils.create(pretty).toJson(map);
  }
}
