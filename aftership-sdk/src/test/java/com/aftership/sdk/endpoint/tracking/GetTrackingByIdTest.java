package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.tracking.GetTrackingParams;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.utils.JsonUtils;
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class GetTrackingByIdTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/tracking/GetTrackingResult.json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void testGetTracking()
      throws IOException, InterruptedException, AftershipException, URISyntaxException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    System.out.println(">>>>> getTracking(String id, GetTrackingParams optionalParams)");
    String query = TestUtil.getJson("endpoint/tracking/GetTrackingParams.json");
    String id = "100";
    Tracking tracking =
        afterShip
            .getTrackingEndpoint()
            .getTracking(id, JsonUtils.create().fromJson(query, GetTrackingParams.class));

    Assertions.assertNotNull(tracking);
    Assertions.assertEquals("fedex", tracking.getSlug(), "Slug mismatch.");
    Assertions.assertTrue(
        tracking.getCheckpoints().size() > 0, "Checkpoints need to be " + "greater than 0");
    Assertions.assertEquals(
        "InfoReceived", tracking.getCheckpoints().get(0).getTag(), "tag mismatch.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        MessageFormat.format("/v4/trackings/{0}", id),
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");
    Assertions.assertNotNull(tracking.getCourierRedirectLink(), "courier_redirect_link mismatch");

    TestUtil.printResponse(afterShip, tracking);
    TestUtil.printRequest(recordedRequest);
  }
}
