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
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ReTrackByIdTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/tracking/ReTrackResult.json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void testReTrack()
      throws IOException, InterruptedException, AftershipException, URISyntaxException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    System.out.println(">>>>> reTrack(String id)");
    String id = "100";
    Tracking tracking = afterShip.getTrackingEndpoint().reTrack(id);

    Assertions.assertNotNull(tracking);
    Assertions.assertEquals("russian-post", tracking.getSlug(), "slug mismatch.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("POST", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        MessageFormat.format("/tracking/2023-10/trackings/{0}/retrack", id),
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");

    TestUtil.printResponse(afterShip, tracking);
    TestUtil.printRequest(recordedRequest);
  }
}
