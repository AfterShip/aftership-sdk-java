package com.aftership.sdk.endpoint;

import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.DataEntity;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class TestError {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse()
                .setResponseCode(401)
                .setBody(TestUtil.getJson("Error.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void testError() {
        AftershipOption option = new AftershipOption();
        option.setEndpoint(String.format(TestUtil.ENDPOINT_FORMAT, server.getPort()));
        AfterShip afterShip = new AfterShip(TestUtil.YOUR_API_KEY, option);

        DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listCouriers();
        Assertions.assertTrue(entity.hasError(),"Entity returned with an error.");
        Assertions.assertEquals(401, entity.getError().getCode().intValue(),"Incorrect error code returned.");
        Assertions.assertEquals("Invalid API Key.", entity.getError().getMessage(), "The message returned is incorrect.");
        Assertions.assertEquals("Unauthorized", entity.getError().getType(), "The type returned is incorrect.");

        TestUtil.printResponse(afterShip, entity);
    }
}
