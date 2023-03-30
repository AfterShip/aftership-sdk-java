package com.aftership.sample.retry;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.model.AftershipOption;

import java.util.Arrays;

public class RetrySample {
  public static void main(String[] args) {
    AftershipOption option = SampleUtil.getAftershipOption();
    option.setRetryCount(10);
    option.setRetryDelay(500);
    option.setRetryMaxDelay(18 * 1000L);
    option.setRetryConditions(Arrays.asList(
      ((response, exception) -> response != null && response.code() > 500),
      ((response, exception) -> exception != null)
    ));
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), option);
  }
}
