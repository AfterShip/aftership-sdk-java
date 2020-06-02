package com.aftership.sdk.endpoint.courier;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ListCouriersTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/courier/ListCouriersResult.json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @BeforeEach
  void initialize() {}

  @Test
  @Order(1)
  public void testListCouriers()
      throws AftershipException, InterruptedException, URISyntaxException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    System.out.println(">>>>> listCouriers()");
    CourierList courierList = afterShip.getCourierEndpoint().listCouriers();

    Assertions.assertNotNull(courierList);
    Assertions.assertEquals(2, courierList.getTotal(), "Total mismatch.");
    Assertions.assertEquals(
        "DHL",
        courierList.getCouriers().get(0).getName(),
        "The first couriers name was incorrect.");
    Assertions.assertEquals(
        "tracking_ship_date",
        courierList.getCouriers().get(1).getRequiredFields().get(0),
        "The first required_fields of the second couriers are tracking_ship_date.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        "/v4/couriers",
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");

    TestUtil.printResponse(afterShip, courierList);
    TestUtil.printRequest(recordedRequest);
  }
}
