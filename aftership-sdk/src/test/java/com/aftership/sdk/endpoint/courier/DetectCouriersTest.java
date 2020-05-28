package com.aftership.sdk.endpoint.courier;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
import com.aftership.sdk.utils.JsonUtils;
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class DetectCouriersTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/courier/DetectCouriersResult" + ".json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void detectCouriers()
      throws AftershipException, IOException, InterruptedException, URISyntaxException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    System.out.println(">>>>> detectCouriers(CourierDetectTracking detectTracking)");
    String requestBody = TestUtil.getJson("endpoint/courier/CourierDetectRequest.json");
    CourierDetectRequest courierDetectRequest =
        JsonUtils.create().fromJson(requestBody, CourierDetectRequest.class);

    CourierDetectList courierDetectList =
        afterShip.getCourierEndpoint().detectCouriers(courierDetectRequest.getTracking());

    Assertions.assertNotNull(courierDetectList);
    Assertions.assertNull(courierDetectList.getTracking(), "Incorrect TRACKING field for response");
    Assertions.assertEquals(
        "tracking_postal_code",
        courierDetectList.getCouriers().get(1).getRequiredFields().get(0),
        "Incorrect TRACKING_POSTAL_CODE field for the response");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("POST", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        "/v4/couriers/detect",
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");

    TestUtil.printResponse(afterShip, courierDetectList);
    TestUtil.printRequest(recordedRequest);
  }
}
