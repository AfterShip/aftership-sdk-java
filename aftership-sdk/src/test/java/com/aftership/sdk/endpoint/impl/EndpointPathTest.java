package com.aftership.sdk.endpoint.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EndpointPathTest {

  @Test
  void testEndpointPathClass()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.endpoint.impl.EndpointPath").newInstance()
            instanceof EndpointPath);
  }
}
