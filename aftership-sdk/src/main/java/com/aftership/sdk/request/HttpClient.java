package com.aftership.sdk.request;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/** Create OkHttpClient */
class HttpClient {
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

    // register a hook for shutdown OkHttpClient.
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  HttpClient.shutdown();
                  System.out.println("OkHttpClient has been Shutdown");
                }));
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

  /** Close network connection. */
  public static void shutdown() {
    if (client != null) {
      client.dispatcher().executorService().shutdown();
      client.connectionPool().evictAll();
      try {
        Cache cache = client.cache();
        if (cache != null) {
          cache.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
