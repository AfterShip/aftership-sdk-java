package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.tracking.NewTrackingRequest;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.utils.JsonUtils;
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class CreateTrackingTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/tracking/CreateTrackingResult.json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void testCreateTrackingNewVersion()
    throws IOException, InterruptedException, AftershipException, URISyntaxException {
    AfterShip afterShip = TestUtil.createAfterShipNewVersion(server);

    System.out.println(">>>>> createTracking(NewTracking newTracking)");
    String requestBody = TestUtil.getJson("endpoint/tracking/CreateTrackingRequest.json");

    Tracking tracking =
      afterShip
        .getTrackingEndpoint()
        .createTracking(
          JsonUtils.getGson().fromJson(requestBody, NewTrackingRequest.class).getTracking());

    Assertions.assertNotNull(tracking);
    Assertions.assertEquals("fedex", tracking.getSlug(), "Slug mismatch.");
    Assertions.assertEquals(
      "2019-05-20", tracking.getOrderPromisedDeliveryDate(), "date mismatch.");
    Assertions.assertEquals(0, tracking.getSmses().size(), "Smses size mismatch.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("POST", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
      "/tracking/2023-10/trackings",
      new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
      "path mismatch.");
    Assertions.assertEquals(
      "YOUR_API_KEY",
      recordedRequest.getHeader("as-api-key"),
      "path mismatch.");
    Assertions.assertEquals(
      null,
      recordedRequest.getHeader("aftership-api-key"),
      "path mismatch.");

    TestUtil.printResponse(afterShip, tracking);
    TestUtil.printRequest(recordedRequest);
  }
}
