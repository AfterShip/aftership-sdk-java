package com.aftership.sdk.error;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.aftership.sdk.exception.ErrorType;

class ErrorTypeTest {
  @Test
  void get() {
    Assertions.assertEquals(ErrorType.ConstructorError, ErrorType.get("ConstructorError"));
    Assertions.assertNull(ErrorType.get(""));
  }

  @Test
  void isErrorType() {
    Assertions.assertTrue(ErrorType.isErrorType("ConstructorError"));
    Assertions.assertFalse(ErrorType.isErrorType("Constructor123"));
  }
}
