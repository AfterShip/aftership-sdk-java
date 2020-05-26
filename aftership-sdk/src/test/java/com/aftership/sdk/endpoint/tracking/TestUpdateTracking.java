package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.model.tracking.UpdateTrackingRequest;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.utils.JsonUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestUpdateTracking {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson(
                "endpoint/tracking/UpdateTrackingResult.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testTestUpdateTracking() throws IOException, InterruptedException {
        AfterShip afterShip = TestUtil.createAfterShip(server);

        //request
        String requestBody = TestUtil.getJson("endpoint/tracking/UpdateTrackingRequest.json");
        UpdateTrackingRequest request = JsonUtils.create().fromJson(requestBody, UpdateTrackingRequest.class);

        SingleTrackingParam param = new SingleTrackingParam();
        param.setId("100");
        DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().updateTracking(param, request);

        //assert
        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getTracking(), "Response data cannot be empty.");
        Assertions.assertEquals("fedex", entity.getData().getTracking().getSlug(), "Slug mismatch.");
        Assertions.assertEquals("InfoReceived", entity.getData().getTracking().getCheckpoints().get(0).getTag(),
                "checkpoints::tag mismatch.");

        RecordedRequest recordedRequest = server.takeRequest();
        Assertions.assertEquals("PUT", recordedRequest.getMethod(), "Method mismatch.");

        //output
        TestUtil.printResponse(afterShip, entity);
        System.out.println("Path: " + recordedRequest.getPath());
        System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());
    }

}
