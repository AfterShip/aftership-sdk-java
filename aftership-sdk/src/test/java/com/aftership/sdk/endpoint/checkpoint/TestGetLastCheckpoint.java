package com.aftership.sdk.endpoint.checkpoint;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.lib.UrlUtil;
import com.aftership.sdk.model.checkpoint.GetLastCheckpointParam;
import com.aftership.sdk.model.checkpoint.LastCheckpoint;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestGetLastCheckpoint {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(
                TestUtil.getJson("checkpoint/GetLastCheckpointResult.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testGetLastCheckpoint() throws IOException, InterruptedException {
        AfterShip afterShip = TestUtil.createAfterShip(server);

        SingleTrackingParam param = new SingleTrackingParam();
        param.setId("100");
        GetLastCheckpointParam optionalParams = new GetLastCheckpointParam();
        optionalParams.setFields("slug,city");
        optionalParams.setLang("china-post");

        DataEntity<LastCheckpoint> entity = afterShip.getCheckpointEndpoint()
                .getLastCheckpoint(param, optionalParams);

        //assert
        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData().getCheckpoint(), "Response data cannot be empty.");
        Assertions.assertEquals("fedex", entity.getData().getSlug(), "slug mismatch.");
        Assertions.assertEquals("Deal", entity.getData().getCheckpoint().getCity(), "city mismatch.");

        RecordedRequest recordedRequest = server.takeRequest();
        Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");

        //output
        TestUtil.printResponse(afterShip, entity);
        System.out.println("Path: " + UrlUtil.decode(recordedRequest.getPath()));
        System.out.println("RequestBody: " + recordedRequest.getBody().readUtf8());

    }

}
