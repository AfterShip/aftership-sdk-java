package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.model.tracking.MarkAsCompletedRequest;
import com.aftership.sdk.model.tracking.MarkAsCompletedRequest.ReasonKind;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestMarkAsCompleted {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/tracking/MarkAsCompletedResult.json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void testMarkAsCompleted() throws IOException, InterruptedException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    // request
    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("100");

    MarkAsCompletedRequest request = new MarkAsCompletedRequest(ReasonKind.LOST);
    DataEntity<SingleTracking> entity =
        afterShip.getTrackingEndpoint().markAsCompleted(param, request);

    // assert
    Assertions.assertFalse(entity.hasError(), "No errors in response.");
    Assertions.assertNotNull(entity.getData().getTracking(), "Response data cannot be empty.");
    Assertions.assertEquals(
        "5b7658cec7c33c0e007de3c5", entity.getData().getTracking().getId(), "id  mismatch.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("POST", recordedRequest.getMethod(), "Method mismatch.");

    // output
    TestUtil.printResponse(afterShip, entity);
    System.out.println("Path: " + recordedRequest.getPath());
    System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());
  }
}
