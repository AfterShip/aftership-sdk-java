package com.aftership.sdk.endpoint.notification;

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
import com.aftership.sdk.model.notification.Notification;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class GetNotificationBySlugTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/notification/GetNotificationResult.json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void testGetNotification()
      throws IOException, InterruptedException, AftershipException, URISyntaxException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    System.out.println(">>>>> getNotification(SlugTrackingNumber identifier)");
    SlugTrackingNumber identifier = new SlugTrackingNumber("fedex", "111111111111");
    Notification notification = afterShip.getNotificationEndpoint().getNotification(identifier);

    Assertions.assertNotNull(notification);
    Assertions.assertEquals(2, notification.getEmails().length, "emails size mismatch.");
    Assertions.assertEquals("+85291239123", notification.getSmses()[0], "smses mismatch.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        MessageFormat.format(
            "/tracking/2023-10/notifications/{0}/{1}", identifier.getSlug(), identifier.getTrackingNumber()),
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");

    TestUtil.printResponse(afterShip, notification);
    TestUtil.printRequest(recordedRequest);
  }
}
