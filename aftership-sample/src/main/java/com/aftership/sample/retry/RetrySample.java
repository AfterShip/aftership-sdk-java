package com.aftership.sample.retry;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.model.AftershipOption;
import com.aftership.sdk.model.RetryOption;

import java.util.Arrays;

public class RetrySample {
  public static void main(String[] args) {
    AftershipOption option = SampleUtil.getAftershipOption();

    RetryOption retryOption = new RetryOption();
    retryOption.setRetryDelay(500);
    retryOption.setRetryMaxDelay(18 * 1000L);
    retryOption.setRetryCount(10);
    retryOption.setRetryConditions(Arrays.asList((
      (response, exception) -> exception != null || (response != null && response.code() >= 500))));

    option.setRetryOption(retryOption);
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), option);
  }
}
