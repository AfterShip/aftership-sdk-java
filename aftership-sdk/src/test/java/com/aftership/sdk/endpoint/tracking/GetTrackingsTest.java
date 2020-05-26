package com.aftership.sdk.endpoint.tracking;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.model.tracking.MultiTrackingsData;
import com.aftership.sdk.model.tracking.MultiTrackingsParams;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;

public class GetTrackingsTest {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson(
                "endpoint/tracking/GetTrackings.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testGetTrackings() throws IOException, InterruptedException {
        AfterShip afterShip = TestUtil.createAfterShip(server);

        MultiTrackingsParams optionalParams = new MultiTrackingsParams();
        optionalParams.setFields("title,order_id");
        optionalParams.setLang("china-post");
        optionalParams.setLimit(10);

        DataEntity<MultiTrackingsData> entity = afterShip.getTrackingEndpoint().getTrackings(optionalParams);

        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getTrackings(), "Response data cannot be empty.");
        Assertions.assertEquals(1, entity.getData().getPage(), "Page value mismatch");
        Assertions.assertEquals(3, entity.getData().getCount(), "Count value mismatch");
        Assertions.assertEquals("usps", entity.getData().getTrackings().get(2).getSlug(), "Slug value mismatch");
        Assertions.assertEquals("InfoReceived", entity.getData().getTrackings().get(0).getCheckpoints().get(0).getTag(),
                "Tag value mismatch");

        TestUtil.printResponse(afterShip, entity);
        System.out.println(server.takeRequest().getPath());

    }

}
