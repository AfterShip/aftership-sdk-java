package com.aftership.sample;

import com.aftership.sdk.config.EnvGetter;
import com.aftership.sdk.model.AftershipOption;

/**
 * Util for Samples
 */
public final class SampleUtil {
  private static final String ENV_API_KEY = "Aftership-Sample-ApiKey";

  private static final String ENV_API_SECRET = "Aftership-Sample-ApiSecret";

  private static final String ENV_AFTERSHIP_OPTION = "Aftership-Sample-Endpoint";

  public static final String getApiKey() {
    return EnvGetter.getString(ENV_API_KEY, "YOUR_API_KEY");
  }

  public static final String getApiSecret() {
    return EnvGetter.getString(ENV_API_SECRET, "YOUR_API_SECRET");
  }

  public static final AftershipOption getAftershipOption() {
    AftershipOption option = new AftershipOption();
    String endpoint = EnvGetter.getString(ENV_AFTERSHIP_OPTION, "https://api.aftership.com/v4");
    option.setEndpoint(endpoint);
    return option;
  }

}
