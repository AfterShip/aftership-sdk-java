package com.aftership.sdk.endpoint.courier;

import org.junit.jupiter.api.*;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.rest.HttpMethod;
import com.aftership.sdk.rest.ResponseEntity;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestListAllCouriers {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson(
                "endpoint/courier/ListAllCouriersResult.json")));
        server.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        server.shutdown();
    }

    @BeforeEach
    void initialize() {
    }

    @Test
    @Order(1)
    public void testListAllCouriers() throws IOException, InterruptedException {
        AftershipOption option = new AftershipOption();
        option.setEndpoint(String.format(TestUtil.ENDPOINT_FORMAT, server.getPort()));
        AfterShip afterShip = new AfterShip(TestUtil.YOUR_API_KEY, option);

        DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listAllCouriers();

        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData(), "Response data cannot be empty.");
        Assertions.assertEquals(3, entity.getData().getTotal(), "Total mismatch.");
        Assertions.assertEquals("India Post International", entity.getData().getCouriers().get(0).getName(),
                "The first couriers name was incorrect.");
        Assertions.assertEquals(0, entity.getData().getCouriers().get(1).getRequiredFields().size(),
                "The array of required_field for the second couriers is empty.");
        Assertions.assertEquals(200, ((ResponseEntity<CourierList>) entity).getResponse().getMeta().getCode(),
                "The code for the response is 200.");

        TestUtil.printResponse(afterShip, entity);

        RecordedRequest recordedRequest = server.takeRequest();
        Assertions.assertEquals(HttpMethod.GET.getName(), recordedRequest.getMethod(), "HttpMethod is GET.");
        Assertions.assertEquals(TestUtil.getRequestPath(EndpointPath.LIST_ALL_COURIERS), recordedRequest.getPath(),
                "The " +
                "requested Path doesn't match.");
    }

}
