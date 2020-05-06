package com.aftership.sdk.lib;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class HttpUtil {
    private static final long TIMEOUT = 50 * 1000L;//TODO: config with env

    private static HttpUtil util;
    private static OkHttpClient client;

    private HttpUtil() {
        client = new OkHttpClient.Builder()
                .callTimeout(50000, TimeUnit.MILLISECONDS)
                .build();
    }

    public static HttpUtil getInstance() {
        if (util == null) {
            synchronized (HttpUtil.class) {
                if (util == null) {
                    util = new HttpUtil();
                }
            }
        }
        return util;
    }


}
