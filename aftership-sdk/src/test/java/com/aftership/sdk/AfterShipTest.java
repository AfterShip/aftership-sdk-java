package com.aftership.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.AftershipOption;

class AfterShipTest {

  @Test
  void testAfterShipConstructorApiKeyAndAftershipOption() throws SdkException {
    AftershipOption options = new AftershipOption();
    options.setEndpoint("https://api.aftership.com/v4");
    options.setUserAgentPrefix("aftership-sdk-java");
    AfterShip afterShip = new AfterShip("API key", options);
    Assertions.assertEquals("API key", afterShip.getApiKey());
    Assertions.assertEquals("aftership-sdk-java", afterShip.getUserAgentPrefix());
    Assertions.assertEquals("https://api.aftership.com/v4", afterShip.getEndpoint());
  }

  @Test
  void testAfterShipConstructorApiKey() throws SdkException {
    AfterShip afterShip = new AfterShip("API key");
    Assertions.assertEquals("API key", afterShip.getApiKey());
  }

  @Test
  void testAfterShipConstructorNullApiKey() {
    Assertions.assertThrows(
        SdkException.class,
        () -> {
          new AfterShip("");
        });
  }

  @Test
  void testAfterShipConstructorEndpoint() throws SdkException {
    AftershipOption options = new AftershipOption();
    options.setUserAgentPrefix("aftership-sdk-java");
    AfterShip afterShip = new AfterShip("API key", options);
    Assertions.assertEquals("https://api.aftership.com/v4", afterShip.getEndpoint());
  }


}
