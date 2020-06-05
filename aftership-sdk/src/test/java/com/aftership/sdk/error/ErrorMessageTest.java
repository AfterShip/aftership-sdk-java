package com.aftership.sdk.error;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.aftership.sdk.exception.ErrorMessage;
import lombok.SneakyThrows;

class ErrorMessageTest {
  @SneakyThrows
  @Test
  void classTest() {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.exception.ErrorMessage").newInstance() instanceof ErrorMessage);
  }
}