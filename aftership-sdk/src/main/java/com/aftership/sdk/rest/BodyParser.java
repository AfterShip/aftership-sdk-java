package com.aftership.sdk.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.aftership.sdk.lib.JsonUtil;
import com.aftership.sdk.lib.StrUtil;
import com.aftership.sdk.model.Meta;

/**
 * Parsing the response message body
 *
 * @author chenjunbiao
 */
public class BodyParser {

  /**
   * Get the Meta object from the message body
   *
   * @param jsonBody the response message body
   * @return Object of Meta
   */
  public static Meta processMeta(String jsonBody) {
    return processMeta(
        JsonParser.parseString(StrUtil.isNotBlank(jsonBody) ? jsonBody : "{}").getAsJsonObject());
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

    return JsonUtil.create().fromJson(metaJson, Meta.class);
  }

  /**
   * Get the T object from the message body
   *
   * @param jsonObject 'com.google.gson.JsonObject' of message body
   * @param responseType type of Class
   * @param <T> Class
   * @return
   */
  public static <T> T processData(JsonObject jsonObject, Class<T> responseType) {
    JsonElement dataJson = jsonObject.get("data");
    if (dataJson == null) {
      return null;
    }
    return JsonUtil.create().fromJson(dataJson, responseType);
  }
}
