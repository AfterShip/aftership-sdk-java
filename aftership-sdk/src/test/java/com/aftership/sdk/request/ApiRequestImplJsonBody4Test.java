package com.aftership.sdk.request;

import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.ErrorMessage;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ApiRequestImplJsonBody4Test {

  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(TestUtil.createMockResponse()
            .setResponseCode(422)
            .setBody("{\"meta\":{\"code\":42200,\"type\":\"UnprocessableEntity\",\"message\":\"The data given in the" +
                    " request failed validation.\"},\"data\":null}"));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  void makeRequest() throws SdkException {
    AfterShip afterShip = TestUtil.createAfterShip(server);
    try {
      afterShip.getCourierEndpoint().listCouriers();
    } catch (ApiException | RequestException e) {
      Assertions.assertEquals(e.getCode(), 42200);
      Assertions.assertEquals(e.getType(), "UnprocessableEntity");
    }
  }

}
