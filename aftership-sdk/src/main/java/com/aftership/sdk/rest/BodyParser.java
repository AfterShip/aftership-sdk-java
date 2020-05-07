package com.aftership.sdk.rest;

import com.aftership.sdk.lib.JsonUtil;
import com.aftership.sdk.lib.StrUtil;
import com.aftership.sdk.model.Meta;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.ResponseBody;

import java.io.IOException;

public class BodyParser {

    public static Meta processMeta(ResponseBody responseBody) {
        String jsonBody;
        try {
            jsonBody = StrUtil.isNotBlank(responseBody.string()) ? responseBody.string() : "{}";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return processMeta(JsonParser.parseString(jsonBody).getAsJsonObject());
    }

    public static Meta processMeta(JsonObject jsonObject){
        JsonElement metaJson = jsonObject.get("meta");
        if (metaJson == null) {
            return null;
        }

        return JsonUtil.create().fromJson(metaJson, Meta.class);
    }

    public static <T> T processData(JsonObject jsonObject, Class<T> responseType){
        JsonElement dataJson = jsonObject.get("data");
        if(dataJson == null){
            return null;
        }
        return JsonUtil.create().fromJson(dataJson, responseType);
    }

}
