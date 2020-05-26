package com.aftership.sdk.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lombok.SneakyThrows;

class DefineTest {

  @SneakyThrows
  @Test
  void testDefineClass() {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.utils.Define").newInstance() instanceof Define);
  }

}