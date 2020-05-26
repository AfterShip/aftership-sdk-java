package com.aftership.sdk.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lombok.SneakyThrows;

class StrUtilTest {

  @SneakyThrows
  @Test
  void testStrUtilClass(){
    Assertions.assertTrue(Class.forName("com.aftership.sdk.utils.StrUtils").newInstance() instanceof StrUtils);
  }

  @Test
  void isNotBlank() {
    Assertions.assertTrue(StrUtils.isNotBlank("abc"));
    Assertions.assertFalse(StrUtils.isNotBlank(""));
  }

  @Test
  void isBlank() {
    Assertions.assertTrue(StrUtils.isBlank(""));
    Assertions.assertFalse(StrUtils.isBlank("abc"));
    Assertions.assertTrue(StrUtils.isBlank(null));
    Assertions.assertTrue(StrUtils.isBlank(" "));
  }

  @Test
  void uuid4() {
    Assertions.assertEquals(36, StrUtils.uuid4().length());
  }
}
