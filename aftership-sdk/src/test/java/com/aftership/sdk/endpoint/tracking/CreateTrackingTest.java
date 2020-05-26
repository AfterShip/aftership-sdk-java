package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.model.tracking.CreateTrackingRequest;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.utils.JsonUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class CreateTrackingTest {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson(
                "endpoint/tracking/CreateTrackingResult.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testCreateTracking() throws IOException, InterruptedException {
        AfterShip afterShip = TestUtil.createAfterShip(server);

        //request
        String requestBody = TestUtil.getJson("endpoint/tracking/CreateTrackingRequest.json");
        CreateTrackingRequest request = JsonUtils.create().fromJson(requestBody, CreateTrackingRequest.class);

        DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().createTracking(request);

        //assert
        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getTracking(), "Response data cannot be empty.");
        Assertions.assertEquals("fedex", entity.getData().getTracking().getSlug(), "Slug mismatch.");
        Assertions.assertEquals("2019-05-20", entity.getData().getTracking().getOrderPromisedDeliveryDate(),
                "order_promised_delivery_date mismatch.");
        Assertions.assertEquals(0, entity.getData().getTracking().getSmses().size(),
                "Smses size mismatch.");

        RecordedRequest recordedRequest = server.takeRequest();
        Assertions.assertEquals("POST", recordedRequest.getMethod(), "Method mismatch.");

        //output
        TestUtil.printResponse(afterShip, entity);
        System.out.println("Path: " + recordedRequest.getPath());
        System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());
    }

}
