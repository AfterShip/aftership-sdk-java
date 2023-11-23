package com.aftership.sdk;

import com.aftership.sdk.enums.Versions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.AftershipOption;

class AfterShipTest {

  @Test
  void testAfterShipConstructorApiKeyAndAftershipOption() throws SdkException {
    AftershipOption options = new AftershipOption();
    options.setEndpoint("https://api.aftership.com/tracking/2023-10");
    options.setUserAgentPrefix("aftership-sdk-java");
    AfterShip afterShip = new AfterShip("API key", options);
    Assertions.assertEquals("API key", afterShip.getApiKey());
    Assertions.assertEquals("aftership-sdk-java", afterShip.getUserAgentPrefix());
    Assertions.assertEquals("https://api.aftership.com/tracking/2023-10", afterShip.getEndpoint());
  }
  @Test
  void testAfterShipConstructorVersion() throws SdkException {
    AftershipOption options = new AftershipOption();
    options.setVersion(Versions.V2023_10);
    options.setUserAgentPrefix("aftership-sdk-java");
    AfterShip afterShip = new AfterShip("API key", options);
    Assertions.assertEquals("2023-10", afterShip.getVersion());
    Assertions.assertEquals("API key", afterShip.getApiKey());
    Assertions.assertEquals("aftership-sdk-java", afterShip.getUserAgentPrefix());
    Assertions.assertEquals("https://api.aftership.com/tracking/2023-10", afterShip.getEndpoint());
  }

  @Test
  void testAfterShipConstructorApiKey() throws SdkException {
    AfterShip afterShip = new AfterShip("API key");
    Assertions.assertEquals("API key", afterShip.getApiKey());
  }

  @Test
  void testAfterShipConstructorNullApiKey() {
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> {
          new AfterShip("");
        });
  }

  @Test
  void testAfterShipConstructorEndpoint() throws SdkException {
    AftershipOption options = new AftershipOption();
    options.setUserAgentPrefix("aftership-sdk-java");
    AfterShip afterShip = new AfterShip("API key", options);
    Assertions.assertEquals("https://api.aftership.com/tracking/2023-10", afterShip.getEndpoint());
  }


}
