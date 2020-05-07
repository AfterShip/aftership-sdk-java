package com.aftership.sdk.rest;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class HttpClient {
    private static final long TIMEOUT = 50 * 1000L;//TODO: set with config

    private static final OkHttpClient client;

    static {
        client = new OkHttpClient.Builder()
                .callTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    private HttpClient(){
    }

    public static OkHttpClient getClient(){
        return client;
    }

}
