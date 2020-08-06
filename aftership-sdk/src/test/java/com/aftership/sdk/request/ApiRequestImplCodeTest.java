package com.aftership.sdk.request;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.SdkException;
import okhttp3.mockwebserver.MockWebServer;

class ApiRequestImplCodeTest {

  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(
                "{\"meta\":{\"code\":401,\"message\":\"InvalidAPIKey.\",\"type\":\"Unauthorized\"},\"data\":{}}"));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  void makeRequest() throws SdkException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    Assertions.assertThrows(
        ApiException.class,
        () -> {
          afterShip.getCourierEndpoint().listCouriers();
        });
  }
}
