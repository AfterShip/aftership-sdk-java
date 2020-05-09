package com.aftership.sdk.error;

import com.aftership.sdk.lib.StrUtil;

public class ErrorMessage {
    public static final String ConstructorInvalidApiKey = "ConstructorError: Invalid API key";
    public static final String ConstructorInvalidRequestConfig = "ConstructorError: Invalid requestConfig";
    public static final String ConstructorInvalidResponseType = "ConstructorError: Invalid responseType";
    public static final String constructorInvalidTrackingNumber = "ConstructorError: tracking_number";

    public static final String HandlerEmptyBody = "HandlerError: Body is empty";
    public static final String HandlerNullMeta = "HandlerError: Meta is Null";
    public static final String HandlerBodyNotJsonObject = "Body is not a Json object.";
    public static final String HandlerInvalidBody = "HandlerError: Invalid Body value";


}
