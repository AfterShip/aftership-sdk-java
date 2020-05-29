package com.aftership.sdk.error;

/** Definition of ErrorMessage */
public class ErrorMessage {
  public static final String CONSTRUCTOR_API_KEY_IS_NULL = "ConstructorError: API key is null";
  public static final String CONSTRUCTOR_INVALID_REQUEST_CONFIG =
      "ConstructorError: Invalid requestConfig";
  public static final String CONSTRUCTOR_RESPONSE_TYPE_IS_NULL =
      "ConstructorError: ResponseType is null";
  public static final String CONSTRUCTOR_INVALID_TRACKING_NUMBER =
      "ConstructorError: tracking_number";
  public static final String CONSTRUCTOR_PATH_IS_EMPTY = "ConstructorError: The path is empty";
  public static final String CONSTRUCTOR_REQUIRED_TRACKING_NUMBER =
      "ConstructorError: Required tracking number";
  public static final String CONSTRUCTOR_REQUIRED_TRACKING_ID =
      "ConstructorError: Required tracking id";
  public static final String CONSTRUCTOR_REQUIRED_SLUG = "ConstructorError: Required tracking slug";
  public static final String CONSTRUCTOR_REQUIRED_PATH = "ConstructorError: Required tracking path";
  public static final String CONSTRUCTOR_PARAM_IS_NULL =
      "ConstructorError: Parameters cannot be null";

  public static final String HANDLER_RESPONSE_BODY_IS_EMPTY =
      "HandlerError: return body json string is empty";
  public static final String HANDLER_RESPONSE_BODY_OBJECT_IS_NULL =
      "HandlerError: return response body object is null";
  public static final String HANDLER_RESPONSE_BODY_IS_NOT_JSON_OBJECT =
      "HandlerError: return response body is not a json object.";
  public static final String HANDLER_RESPONSE_META_IS_NULL = "HandlerError: return meta is Null";
}
