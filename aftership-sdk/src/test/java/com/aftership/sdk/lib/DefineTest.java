package com.aftership.sdk.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lombok.SneakyThrows;

class DefineTest {

  @SneakyThrows
  @Test
  void testDefineClass() {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.lib.Define").newInstance() instanceof Define);
  }

}