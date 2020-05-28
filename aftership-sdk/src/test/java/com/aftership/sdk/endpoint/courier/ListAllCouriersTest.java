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

public class ListAllCouriersTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/courier/ListAllCouriersResult.json")));
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
  public void testListAllCouriers()
      throws AftershipException, InterruptedException, URISyntaxException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    System.out.println(">>>>> listAllCouriers()");
    CourierList courierList = afterShip.getCourierEndpoint().listAllCouriers();

    Assertions.assertNotNull(courierList);
    Assertions.assertEquals(3, courierList.getTotal(), "Total mismatch.");
    Assertions.assertEquals(
        "India Post International",
        courierList.getCouriers().get(0).getName(),
        "The first couriers name was incorrect.");
    Assertions.assertEquals(
        0,
        courierList.getCouriers().get(1).getRequiredFields().size(),
        "The array of required_field for the second couriers is empty.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        "/v4/couriers/all",
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");

    TestUtil.printResponse(afterShip, courierList);
    TestUtil.printRequest(recordedRequest);
  }
}
