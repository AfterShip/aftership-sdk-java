package com.aftership.sample;

import java.io.IOException;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.model.AftershipOption;

/** Test for Shutdown OkHttpClient. example: kill pid; kill -2 pid; kill -15 pid; */
public class Shutdown {

  public static void main(String[] args) {
    AftershipOption option = new AftershipOption();
    option.setEndpoint("https://api.aftership.com/tracking/2023-10");
    // Set timeout value, Otherwise, default values are used.
    option.setCallTimeout(10 * 1000);
    option.setConnectTimeout(10 * 1000);
    option.setReadTimeout(10 * 1000);
    option.setWriteTimeout(10 * 1000);
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), option);

    try {
      afterShip.getCourierEndpoint().listCouriers();
    } catch (RequestException | ApiException e) {
      e.printStackTrace();
    } finally {
      try {
        // Shut down the network completely
        afterShip.shutdown();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
