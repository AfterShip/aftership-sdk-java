package com.aftership.sdk.request;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.ErrorMessage;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import okhttp3.mockwebserver.MockWebServer;

class ApiRequestImplJsonBody3Test {

  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(TestUtil.createMockResponse().setBody("{}"));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  void makeRequest() throws SdkException, ApiException {
    AfterShip afterShip = TestUtil.createAfterShip(server);
    try {
      afterShip.getCourierEndpoint().listCouriers();
    } catch (RequestException e) {
      Assertions.assertTrue(e.getMessage().startsWith(ErrorMessage.HANDLER_RESPONSE_BODY_IS_EMPTY));
    }
  }

}
