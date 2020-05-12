package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestDeleteTracking {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson("tracking/DeleteTrackingResult.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testDeleteTracking() throws IOException, InterruptedException {
        AftershipOption option = new AftershipOption();
        option.setEndpoint(String.format(TestUtil.ENDPOINT_FORMAT, server.getPort()));
        AfterShip afterShip = new AfterShip(TestUtil.YOUR_API_KEY, option);

        //request
        SingleTrackingParam param = new SingleTrackingParam();
        param.setId("100");

        DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().deleteTracking(param);

        //assert
        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getTracking(), "Response data cannot be empty.");
        Assertions.assertEquals("fedex", entity.getData().getTracking().getSlug(), "slug mismatch.");
        Assertions.assertNull(entity.getData().getTracking().getTrackingState(), "tracking_state mismatch.");

        RecordedRequest recordedRequest = server.takeRequest();
        Assertions.assertEquals("DELETE", recordedRequest.getMethod(), "Method mismatch.");

        //output
        TestUtil.printResponse(afterShip, entity);
        System.out.println("Path: " + recordedRequest.getPath());
        System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());
    }

}
