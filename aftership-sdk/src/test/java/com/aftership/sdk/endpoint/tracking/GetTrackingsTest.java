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
import com.aftership.sdk.model.tracking.GetTrackingsParams;
import com.aftership.sdk.model.tracking.PagedTrackings;
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class GetTrackingsTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/tracking/GetTrackingsResult.json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void testGetTrackings()
      throws IOException, InterruptedException, AftershipException, URISyntaxException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    System.out.println(">>>>> getTrackings(GetTrackingsParams params)");
    GetTrackingsParams optionalParams = new GetTrackingsParams();
    optionalParams.setFields("title,order_id");
    optionalParams.setLang("china-post");
    optionalParams.setLimit(10);

    PagedTrackings pagedTrackings = afterShip.getTrackingEndpoint().getTrackings(optionalParams);

    Assertions.assertNotNull(pagedTrackings);
    Assertions.assertEquals(1, pagedTrackings.getPage(), "Page value mismatch");
    Assertions.assertEquals(3, pagedTrackings.getCount(), "Count value mismatch");
    Assertions.assertEquals(
        "usps", pagedTrackings.getTrackings().get(2).getSlug(), "Slug value mismatch");
    Assertions.assertEquals(
        "InfoReceived",
        pagedTrackings.getTrackings().get(0).getCheckpoints().get(0).getTag(),
        "Tag value mismatch");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        "/tracking/2023-10/trackings",
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");
    Assertions.assertNotNull(
        pagedTrackings.getTrackings().get(0).getCourierRedirectLink(),
        "courier_redirect_link mismatch");

    TestUtil.printResponse(afterShip, pagedTrackings);
    TestUtil.printRequest(recordedRequest);
  }
}
