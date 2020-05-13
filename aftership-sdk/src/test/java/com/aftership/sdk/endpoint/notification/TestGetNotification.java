package com.aftership.sdk.endpoint.notification;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.lib.UrlUtil;
import com.aftership.sdk.model.notification.SingleNotification;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestGetNotification {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(
                TestUtil.getJson("notification/GetNotificationResult.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testGetNotification() throws IOException, InterruptedException {
        AfterShip afterShip = TestUtil.createAfterShip(server);

        SingleTrackingParam param = new SingleTrackingParam();
        param.setId("100");

        DataEntity<SingleNotification> entity = afterShip.getNotificationEndpoint().getNotification(param);

        //assert
        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getNotification(), "Response data cannot be empty.");
        Assertions.assertEquals(2, entity.getData().getNotification().getEmails().length,
                "emails size mismatch.");
        Assertions.assertEquals("+85291239123", entity.getData().getNotification().getSmses()[0],
                "smses mismatch.");

        RecordedRequest recordedRequest = server.takeRequest();
        Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");

        //output
        TestUtil.printResponse(afterShip, entity);
        System.out.println("Path: " + UrlUtil.decode(recordedRequest.getPath()));
        System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());
    }

}
