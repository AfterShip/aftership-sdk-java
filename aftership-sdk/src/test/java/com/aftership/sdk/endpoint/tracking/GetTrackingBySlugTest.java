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
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.utils.JsonUtils;
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class GetTrackingBySlugTest {
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

    System.out.println(">>>>> getTracking(SlugTrackingNumber identifier, GetTrackingParams optionalParams)");
    String query = TestUtil.getJson("endpoint/tracking/GetTrackingParams.json");
    SlugTrackingNumber identifier = new SlugTrackingNumber("fedex", "111111111111");
    Tracking tracking =
        afterShip
            .getTrackingEndpoint()
            .getTracking(identifier, JsonUtils.getGson().fromJson(query, GetTrackingParams.class));

    Assertions.assertNotNull(tracking);
    Assertions.assertEquals(10, tracking.getTransitTime(), "transit_time mismatch.");
    Assertions.assertEquals("fedex", tracking.getSlug(), "Slug mismatch.");
    Assertions.assertTrue(
        tracking.getCheckpoints().size() > 0, "Checkpoints need to be " + "greater than 0");
    Assertions.assertEquals(
        "InfoReceived", tracking.getCheckpoints().get(0).getTag(), "tag mismatch.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        MessageFormat.format("/tracking/2023-10/trackings/{0}/{1}", identifier.getSlug(), identifier.getTrackingNumber()),
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");
    Assertions.assertNotNull(tracking.getCourierRedirectLink(), "courier_redirect_link mismatch");

    TestUtil.printResponse(afterShip, tracking);
    TestUtil.printRequest(recordedRequest);
  }
}
