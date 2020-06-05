package com.aftership.sdk.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

class RequestExceptionTest {

  @Test
  void testConstructor() {
    Assertions.assertEquals(
        ErrorType.HandlerError.getName(),
        (new RequestException(ErrorType.HandlerError.getName(), "abc", new HashMap<>())).getType());

    Assertions.assertEquals(
        ErrorType.HandlerError.getName(),
        (new RequestException(ErrorType.HandlerError, "abc")).getType());

    Assertions.assertEquals(
        ErrorType.HandlerError.getName(),
        (new RequestException(ErrorType.HandlerError, new RuntimeException())).getType());
  }
}
