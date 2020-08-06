package com.aftership.sdk.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;

class ApiRequestImplMakeRequestParamsTest {

  @Test
  void makeRequestParamsTest() throws SdkException {
    ApiRequestImpl apiRequest = new ApiRequestImpl(null);

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          apiRequest.makeRequest(HttpMethod.GET, "", null, null, CourierList.class);
        });
  }

  @Test
  void makeRequestParamsTest2() throws SdkException {
    AftershipOption option = new AftershipOption();
    AfterShip app = new AfterShip(TestUtil.YOUR_API_KEY, option);
    ApiRequestImpl apiRequest = new ApiRequestImpl(app);

    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          apiRequest.makeRequest(HttpMethod.GET, "/couriers", null, null, null);
        });
  }
}
