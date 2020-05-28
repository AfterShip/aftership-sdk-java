package com.aftership.sdk.error;

/** Definition of ErrorMessage */
public class ErrorMessage {
  public static final String CONSTRUCTOR_INVALID_API_KEY = "ConstructorError: Invalid API key";
  public static final String CONSTRUCTOR_INVALID_REQUEST_CONFIG =
      "ConstructorError: Invalid requestConfig";
  public static final String CONSTRUCTOR_INVALID_RESPONSE_TYPE =
      "ConstructorError: Invalid responseType";
  public static final String CONSTRUCTOR_INVALID_TRACKING_NUMBER =
      "ConstructorError: tracking_number";
  public static final String CONSTRUCTOR_PATH_IS_EMPTY = "ConstructorError: The path is empty";
  public static final String CONSTRUCTOR_REQUIRED_TRACKING_NUMBER =
      "ConstructorError: Required tracking number";
  public static final String CONSTRUCTOR_REQUIRED_TRACKING_ID =
      "ConstructorError: Required tracking id";
  public static final String CONSTRUCTOR_REQUIRED_SLUG =
      "ConstructorError: Required tracking slug";
  public static final String CONSTRUCTOR_REQUIRED_PATH = "ConstructorError: Required tracking path";
  public static final String CONSTRUCTOR_NULL_PARAM = "ConstructorError: Parameters cannot be null";

  public static final String HANDLER_EMPTY_BODY = "HandlerError: Body is empty";
  public static final String HANDLER_NULL_META = "HandlerError: Meta is Null";
  public static final String HANDLER_BODY_NOT_JSON_OBJECT = "Body is not a Json object.";
  public static final String HANDLER_INVALID_BODY = "HandlerError: Invalid Body value";

  public static final String HANDLER_BODY_IS_NULL = "HandlerError: return body is null";
}
