package com.aftership.sdk.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.error.ErrorMessage;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.courier.CourierList;

class ApiRequestImplMakeRequestParamsTest {

  @Test
  void makeRequestParamsTest() throws SdkException {
    ApiRequestImpl apiRequest = new ApiRequestImpl(null);

    try {
      apiRequest.makeRequest(HttpMethod.GET, "", null, null, CourierList.class);
    } catch (SdkException | RequestException | ApiException e) {
      Assertions.assertTrue(e.getMessage().startsWith(ErrorMessage.CONSTRUCTOR_PATH_IS_EMPTY));
    }
  }

  @Test
  void makeRequestParamsTest2() throws SdkException {
    AftershipOption option = new AftershipOption();
    AfterShip app = new AfterShip(TestUtil.YOUR_API_KEY, option);
    ApiRequestImpl apiRequest = new ApiRequestImpl(app);

    try {
      apiRequest.makeRequest(HttpMethod.GET, "/couriers", null, null, null);
    } catch (SdkException | RequestException | ApiException e) {
      System.out.println(e.getMessage());
      Assertions.assertTrue(e.getMessage().startsWith(ErrorMessage.CONSTRUCTOR_RESPONSE_TYPE_IS_NULL));
    }
  }


}
