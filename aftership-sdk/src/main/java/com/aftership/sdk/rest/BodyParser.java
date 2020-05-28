package com.aftership.sdk.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.aftership.sdk.model.Meta;
import com.aftership.sdk.utils.JsonUtils;
import com.aftership.sdk.utils.StrUtils;

/** Parsing the response message body */
public class BodyParser {

  /**
   * Get the Meta object from the message body
   *
   * @param jsonBody the response message body
   * @return Object of Meta
   */
  public static Meta processMeta(String jsonBody) {
    return processMeta(
        JsonParser.parseString(StrUtils.isNotBlank(jsonBody) ? jsonBody : "{}").getAsJsonObject());
  }

  /**
   * Get the Meta object from the message body
   *
   * @param jsonObject the response message body
   * @return Object of Meta
   */
  public static Meta processMeta(JsonObject jsonObject) {
    JsonElement metaJson = jsonObject.get("meta");
    if (metaJson == null) {
      return null;
    }

    return JsonUtils.create().fromJson(metaJson, Meta.class);
  }

  /**
   * Get the T object from the message body
   *
   * @param jsonObject 'com.google.gson.JsonObject' of message body
   * @param responseType type of Class
   * @param <T> Class
   * @return T object
   */
  public static <T> T processData(JsonObject jsonObject, Class<T> responseType) {
    JsonElement dataJson = jsonObject.get("data");
    if (dataJson == null) {
      return null;
    }
    return JsonUtils.create().fromJson(dataJson, responseType);
  }
}
