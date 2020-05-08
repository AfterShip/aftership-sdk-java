package com.aftership.sdk.config;

import com.aftership.sdk.lib.StrUtil;

import java.util.Map;


public final class EnvGetter {

    private static Map<String, String> getEnv(){
        Map<String, String> env = System.getenv();
        return env;
    }

    public static String getString(String key, String defaultValue) {
        String value = getEnv().get(key);
        if(StrUtil.isBlank(value)){
            return defaultValue;
        }
        return value;
    }

    public static int getInt(String key, int defaultValue){
        String value = getEnv().get(key);
        if(StrUtil.isBlank(value)){
            return defaultValue;
        }

        try{
            return Integer.parseInt(value);

        }catch (NumberFormatException ex){
            return defaultValue;
        }
    }

    public static long getLong(String key, long defaultValue) {
        String value = getEnv().get(key);
        if (StrUtil.isBlank(value)) {
            return defaultValue;
        }

        try {
            return Long.parseLong(value);

        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public static boolean getBool(String key, boolean defaultValue){
        String value = getEnv().get(key);
        if (StrUtil.isBlank(value)) {
            return defaultValue;
        }

        try {
            return Boolean.parseBoolean(value);

        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

}
