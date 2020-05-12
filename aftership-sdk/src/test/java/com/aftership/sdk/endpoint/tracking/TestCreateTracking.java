package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.lib.JsonUtil;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.tracking.CreateTrackingRequest;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestCreateTracking {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson("tracking/CreateTrackingResult.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testCreateTracking() throws IOException, InterruptedException {
        AftershipOption option = new AftershipOption();
        option.setEndpoint(String.format(TestUtil.ENDPOINT_FORMAT, server.getPort()));
        AfterShip afterShip = new AfterShip(TestUtil.YOUR_API_KEY, option);

        //request
        String requestBody = TestUtil.getJson("tracking/CreateTrackingRequest.json");
        CreateTrackingRequest request = JsonUtil.create().fromJson(requestBody, CreateTrackingRequest.class);

        DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().createTracking(request);

        //assert
        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getTracking(), "Response data cannot be empty.");
        Assertions.assertEquals("fedex", entity.getData().getTracking().getSlug(), "Slug mismatch.");
        Assertions.assertEquals("2019-05-20", entity.getData().getTracking().getOrderPromisedDeliveryDate(),
                "order_promised_delivery_date mismatch.");
        Assertions.assertEquals(0, entity.getData().getTracking().getSmses().size(),
                "Smses size mismatch.");

        //output
        TestUtil.printResponse(afterShip, entity);
        RecordedRequest recordedRequest = server.takeRequest();
        System.out.println("Path: " + recordedRequest.getPath());
        System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());
    }

}
