package com.aftership.sdk.rest;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/** Create OkHttpClient */
public class HttpClient {
  private static final long TIMEOUT = 50 * 1000L;

  private static final OkHttpClient client;

  static {
    client =
        new OkHttpClient.Builder()
            .callTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .build();
  }

  private HttpClient() {}

  /**
   * get instance of OkHttpClient
   *
   * @return OkHttpClient
   */
  public static OkHttpClient getClient() {
    return client;
  }
}
