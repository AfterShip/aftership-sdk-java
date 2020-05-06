package com.aftership.sdk.lib;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    public static <T> T parseJson(String json, Class<T> tClass){
        if (StrUtil.isBlank(json)){
            return null;
        }
        return create().fromJson(json,tClass);
    }

    public static Gson create(){
        return create(false);
    }

    public static Gson create(boolean pretty){
        GsonBuilder builder = new GsonBuilder();
        if(pretty) {
            builder.setPrettyPrinting();
        }
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.create();
    }
}
