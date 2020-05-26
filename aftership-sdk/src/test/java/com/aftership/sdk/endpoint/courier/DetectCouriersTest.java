package com.aftership.sdk.endpoint.courier;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.utils.JsonUtils;
import okhttp3.mockwebserver.MockWebServer;

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
  public void detectCouriers() throws IOException, InterruptedException {
    AftershipOption option = new AftershipOption();
    option.setEndpoint(String.format(TestUtil.ENDPOINT_FORMAT, server.getPort()));
    AfterShip afterShip = new AfterShip(TestUtil.YOUR_API_KEY, option);

    String requestBody = TestUtil.getJson("endpoint/courier/CourierDetectRequest.json");
    CourierDetectRequest courierDetectRequest =
        JsonUtils.create().fromJson(requestBody, CourierDetectRequest.class);
    DataEntity<CourierDetectList> entity =
        afterShip.getCourierEndpoint().detectCouriers(courierDetectRequest);

    Assertions.assertFalse(entity.hasError(), "No errors in response.");
    Assertions.assertNotNull(entity.getData(), "Response data cannot be empty.");
    Assertions.assertNull(entity.getData().getTracking(), "Incorrect TRACKING field for response");
    Assertions.assertEquals(
        "tracking_postal_code",
        entity.getData().getCouriers().get(1).getRequiredFields().get(0),
        "Incorrect TRACKING_POSTAL_CODE field for the response");

    Assertions.assertTrue(
        JsonUtils.create()
            .fromJson(server.takeRequest().getBody().readUtf8(), CourierDetectRequest.class)
            .equals(courierDetectRequest),
        "Request's Body is inconsistent.");
  }
}
