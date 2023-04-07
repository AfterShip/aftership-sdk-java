package com.aftership.sdk.request.retry;

import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.RetryOption;
import com.aftership.sdk.model.tracking.GetTrackingParams;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.utils.JsonUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RetryInterceptorTest {

  private MockWebServer mockWebServer;
  private AfterShip aftership;
  private OkHttpClient okHttpClient;

  @BeforeEach
  public void setup() {
    mockWebServer = new MockWebServer();

    RetryOption retryOption = new RetryOption();
    retryOption.setRetryDelay(500);
    retryOption.setRetryMaxDelay(18 * 1000L);
    retryOption.setRetryCount(10);
    retryOption.setRetryConditions(Arrays.asList((
      (response, exception) -> exception != null || (response != null && response.code() >= 500))));

    AftershipOption option = new AftershipOption();
    option.setRetryOption(retryOption);
    option.setEndpoint(String.format(TestUtil.ENDPOINT_FORMAT, mockWebServer.getPort()));
    aftership = new AfterShip(TestUtil.YOUR_API_KEY, option);


    okHttpClient = new OkHttpClient.Builder()
      .addInterceptor(new RetryInterceptor(
        500,
        18 * 1000,
        10,
        Arrays.asList((
          (response, exception) -> exception != null || (response != null && response.code() >= 500)))
      ))
      .build();
  }

  @AfterEach
  public void teardown() throws IOException {
    mockWebServer.shutdown();
  }

  @Test
  public void testSuccessfulRequestWithoutRetries() throws IOException, SdkException, RequestException, ApiException {
    mockWebServer.enqueue(TestUtil.createMockResponse()
      .setBody(TestUtil.getJson("endpoint/tracking/GetTrackingResult.json")).setResponseCode(200));

    String query = TestUtil.getJson("endpoint/tracking/GetTrackingParams.json");
    Tracking tracking = aftership.getTrackingEndpoint()
      .getTracking("100", JsonUtils.getGson().fromJson(query, GetTrackingParams.class));

    assertEquals(tracking.getId(), "5b7658cec7c33c0e007de3c5");
  }

  @Test
  public void testRetryOnStopRetry() throws IOException, SdkException, RequestException, ApiException {
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(400));
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(200));

    Request request = new Request.Builder().url(mockWebServer.url("/")).build();
    Response response = okHttpClient.newCall(request).execute();

    assertEquals(400, response.code());
  }

  @Test
  public void testRetryOnServerError() throws IOException, SdkException, RequestException, ApiException {
    // first request fail
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    // retry
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(new MockResponse().setResponseCode(500));
    mockWebServer.enqueue(TestUtil.createMockResponse()
      .setBody(TestUtil.getJson("endpoint/tracking/GetTrackingResult.json")).setResponseCode(200));

    String query = TestUtil.getJson("endpoint/tracking/GetTrackingParams.json");
    Tracking tracking = aftership.getTrackingEndpoint()
      .getTracking("100", JsonUtils.getGson().fromJson(query, GetTrackingParams.class));

    assertEquals(tracking.getId(), "5b7658cec7c33c0e007de3c5");
  }

}
