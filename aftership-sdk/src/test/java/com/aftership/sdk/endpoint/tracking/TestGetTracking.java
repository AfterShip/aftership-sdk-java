package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.*;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.model.tracking.*;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.utils.JsonUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestGetTracking {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson("endpoint/tracking/GetTracking.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testGetTracking() throws IOException, InterruptedException {
        AfterShip afterShip = TestUtil.createAfterShip(server);

        //request
        String getTrackingParams = TestUtil.getJson("endpoint/tracking/GetTrackingParams.json");
        GetTrackingParams optionalParams = JsonUtils.create().fromJson(getTrackingParams, GetTrackingParams.class);
        SingleTrackingParam param = new SingleTrackingParam();
        param.setId("100");
        param.setOptionalParams(new SingleTrackingOptionalParams());
        param.getOptionalParams().setTrackingPostalCode("abc");
        DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().getTracking(param, optionalParams);

        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getTracking(), "Response data cannot be empty.");

        Assertions.assertEquals("fedex", entity.getData().getTracking().getSlug(), "Slug mismatch.");
        Assertions.assertTrue(entity.getData().getTracking().getCheckpoints().size() > 0, "Checkpoints need to be " +
                "greater than 0");
        Assertions.assertEquals("InfoReceived", entity.getData().getTracking()
                .getCheckpoints().get(0).getTag(), "tag mismatch.");

        RecordedRequest recordedRequest = server.takeRequest();
        Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");

        //output
        TestUtil.printResponse(afterShip, entity);
        System.out.println("Path: " + recordedRequest.getPath());
        System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());
    }

}
