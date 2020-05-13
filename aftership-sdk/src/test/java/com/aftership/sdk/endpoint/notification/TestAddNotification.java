package com.aftership.sdk.endpoint.notification;

import org.junit.jupiter.api.*;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.lib.JsonUtil;
import com.aftership.sdk.lib.UrlUtil;
import com.aftership.sdk.model.notification.SingleNotification;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestAddNotification {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson(
                "endpoint/notification/AddNotificationResult.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @BeforeEach
    void initialize() throws IOException {
        System.out.println("....initialize....");
    }

    @Test
    public void testAddNotification() throws IOException, InterruptedException {
        System.out.println("....testAddNotification....");

        AfterShip afterShip = TestUtil.createAfterShip(server);

        //request
        String requestBody = TestUtil.getJson("endpoint/notification/AddNotificationRequest.json");
        SingleNotification singleNotification = JsonUtil.create().fromJson(requestBody, SingleNotification.class);
        SingleTrackingParam param = new SingleTrackingParam();
        param.setId("100");

        DataEntity<SingleNotification> entity = afterShip.getNotificationEndpoint().addNotification(param,
                singleNotification);

        //assert
        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getNotification(), "Response data cannot be empty.");
        Assertions.assertEquals(2, entity.getData().getNotification().getEmails().length,
                "emails size mismatch.");

        RecordedRequest recordedRequest = server.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod(), "Method mismatch.");
        Assertions.assertEquals("/v4/notifications/100/add", UrlUtil.decode(recordedRequest.getPath()),
                "path mismatch.");

        //output
        TestUtil.printResponse(afterShip, entity);
        System.out.println("Path: " + UrlUtil.decode(recordedRequest.getPath()));
        System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());
    }

}
