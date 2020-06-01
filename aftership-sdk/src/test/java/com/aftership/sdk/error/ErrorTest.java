package com.aftership.sdk.error;

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

public class ErrorTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setResponseCode(401)
            .setBody(TestUtil.getJson("error/Error.json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void testError() throws SdkException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    Assertions.assertThrows(
        ApiException.class,
        () -> {
          try {
            afterShip.getCourierEndpoint().listCouriers();
          } catch (ApiException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getRateLimit());
            ex.printMessage();
            throw ex;
          }
        });
  }
}
