package com.aftership.sdk.endpoint.courier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.rest.HttpMethod;
import com.aftership.sdk.rest.ResponseEntity;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class TestListCouriers {
    public static MockWebServer server;

    @BeforeAll
    static void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(TestUtil.createMockResponse().setBody(TestUtil.getJson("courier/ListCouriersResult.json")));
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
    public void testListCouriers() throws IOException, InterruptedException {
        AftershipOption option = new AftershipOption();
        option.setEndpoint(String.format(TestUtil.ENDPOINT_FORMAT, server.getPort()));
        AfterShip afterShip = new AfterShip(TestUtil.YOUR_API_KEY, option);

        DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listCouriers();

        Assertions.assertFalse(entity.hasError(), "No errors in response.");
        Assertions.assertNotNull(entity.getData(), "Response data cannot be empty.");
        Assertions.assertEquals(2, entity.getData().getTotal(), "Total mismatch.");
        Assertions.assertEquals("DHL", entity.getData().getCouriers().get(0).getName(),
                "The first couriers name was incorrect.");
        Assertions.assertEquals("tracking_ship_date", entity.getData().getCouriers().get(1).getRequiredFields().get(0),
                "The first required_fields of the second couriers are tracking_ship_date.");
        Assertions.assertEquals(200, ((ResponseEntity<CourierList>) entity).getResponse().getMeta().getCode(),
                "The code for the response is 200.");

        TestUtil.printResponse(afterShip, entity);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals(HttpMethod.GET.getName(), recordedRequest.getMethod(), "HttpMethod is GET.");
        assertEquals(TestUtil.getRequestPath(EndpointPath.LIST_COURIERS), recordedRequest.getPath(), "The requested " +
                "Path doesn't match.");
    }

}
