package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.*;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.lib.JsonUtil;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.tracking.*;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;

public class TestGetTracking {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson("tracking/GetTracking.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testGetTracking() throws IOException, InterruptedException {
        AftershipOption option = new AftershipOption();
        option.setEndpoint(String.format(TestUtil.ENDPOINT_FORMAT, server.getPort()));
        AfterShip afterShip = new AfterShip(TestUtil.YOUR_API_KEY, option);

        //request
        String getTrackingParams = TestUtil.getJson("tracking/GetTrackingParams.json");
        GetTrackingParams optionalParams = JsonUtil.create().fromJson(getTrackingParams, GetTrackingParams.class);
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

        TestUtil.printResponse(afterShip, entity);
        System.out.println(server.takeRequest().getPath());
    }

}
