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
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class GetNotificationByIdTest {
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

    System.out.println(">>>>> getNotification(String id)");
    String id = "100";
    Notification notification = afterShip.getNotificationEndpoint().getNotification(id);

    Assertions.assertNotNull(notification);
    Assertions.assertEquals(2, notification.getEmails().length, "emails size mismatch.");
    Assertions.assertEquals("+85291239123", notification.getSmses()[0], "smses mismatch.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        MessageFormat.format("/tracking/2023-10/notifications/{0}", id),
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");

    TestUtil.printResponse(afterShip, notification);
    TestUtil.printRequest(recordedRequest);
  }
}
