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
            .getTracking(id, JsonUtils.getGson().fromJson(query, GetTrackingParams.class));

    Assertions.assertNotNull(tracking);
    Assertions.assertEquals("fedex", tracking.getSlug(), "Slug mismatch.");
    Assertions.assertEquals(10, tracking.getTransitTime(), "transit_time mismatch.");
    Assertions.assertEquals("usps", tracking.getNextCouriers().get(0).getSlug(), "next couriers mismatch.");
    Assertions.assertEquals("61293150000079650811", tracking.getNextCouriers().get(0).getTrackingNumber(), "next couriers mismatch.");
    Assertions.assertEquals("system", tracking.getNextCouriers().get(0).getSource(), "next couriers mismatch.");
    Assertions.assertEquals("8e1261bde336436abbc7cb3eee8cd707", tracking.getCourierConnectionId(), "courier connection id mismatch.");
    Assertions.assertEquals("Carrier EDD", tracking.getFirstEstimatedDelivery().getSource(), "first_estimated_delivery id mismatch.");
    Assertions.assertEquals("specific", tracking.getFirstEstimatedDelivery().getType(), "first_estimated_delivery mismatch.");
    Assertions.assertEquals("2022-01-05T12:11:11+01:00", tracking.getFirstEstimatedDelivery().getDatetime(), "first_estimated_delivery mismatch.");
    Assertions.assertEquals(null, tracking.getFirstEstimatedDelivery().getDatetimeMax(), "first_estimated_delivery mismatch.");
    Assertions.assertEquals(null, tracking.getFirstEstimatedDelivery().getDatetimeMin(), "first_estimated_delivery mismatch.");
    Assertions.assertTrue(
        tracking.getCheckpoints().size() > 0, "Checkpoints need to be " + "greater than 0");
    Assertions.assertEquals(
        "InfoReceived", tracking.getCheckpoints().get(0).getTag(), "tag mismatch.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        MessageFormat.format("/tracking/2023-10/trackings/{0}", id),
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");
    Assertions.assertNotNull(tracking.getCourierRedirectLink(), "courier_redirect_link mismatch");

    TestUtil.printResponse(afterShip, tracking);
    TestUtil.printRequest(recordedRequest);
  }
}
