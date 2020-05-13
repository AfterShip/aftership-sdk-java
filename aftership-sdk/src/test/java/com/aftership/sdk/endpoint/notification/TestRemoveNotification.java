package com.aftership.sdk.endpoint.notification;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.lib.UrlUtil;
import com.aftership.sdk.model.notification.NotificationWrapper;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;


public class TestRemoveNotification {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson(
                "endpoint/notification/RemoveNotificationResult.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testRemoveNotification() throws IOException, InterruptedException {
        AfterShip afterShip = TestUtil.createAfterShip(server);

        //request
        SingleTrackingParam param = new SingleTrackingParam();
        param.setId("100");

        DataEntity<NotificationWrapper> entity = afterShip.getNotificationEndpoint().removeNotification(param);

        //assert
        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getNotification(), "Response data cannot be empty.");
        Assertions.assertEquals(2, entity.getData().getNotification().getEmails().length,
                "emails size mismatch.");

        RecordedRequest recordedRequest = server.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod(), "Method mismatch.");
        Assertions.assertEquals("/v4/notifications/100/remove", UrlUtil.decode(recordedRequest.getPath()),
                "path mismatch.");

        //output
        TestUtil.printResponse(afterShip, entity);
        System.out.println("Path: " + UrlUtil.decode(recordedRequest.getPath()));
        System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());
    }
}
