package com.aftership.sdk.endpoint.estimateddeliverydate;

import java.io.IOException;

import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.estimateddeliverydate.EstimatedDeliveryDateBatchPredictRequest;
import com.aftership.sdk.model.estimateddeliverydate.EstimatedDeliveryDateList;
import com.aftership.sdk.utils.JsonUtils;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class BatchPredictEstimatedDeliveryDateTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
      TestUtil.createMockResponse()
        .setBody(TestUtil.getJson("endpoint/estimateddeliverydate/BatchPredictEstimatedDeliveryDateResult" + ".json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void batchPredictEstimatedDeliveryDate()
    throws AftershipException, IOException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    System.out.println(">>>>> batchPredictEstimatedDeliveryDate(List<EstimatedDeliveryDate> estimatedDeliveryDates)");
    String requestBody = TestUtil.getJson("endpoint/estimateddeliverydate/BatchPredictEstimatedDeliveryDateRequest.json");
    EstimatedDeliveryDateBatchPredictRequest estimatedDeliveryDateBatchPredictRequest =
      JsonUtils.getGson().fromJson(requestBody, EstimatedDeliveryDateBatchPredictRequest.class);

    EstimatedDeliveryDateList estimatedDeliveryDateList =
      afterShip.getEstimatedDeliveryDateEndpoint().batchPredictEstimatedDeliveryDate(estimatedDeliveryDateBatchPredictRequest.getEstimatedDeliveryDates());

    Assertions.assertEquals(1, estimatedDeliveryDateList.getEstimatedDeliveryDates().size());
  }
}
