package com.aftership.sdk.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lombok.SneakyThrows;

class StrUtilTest {

  @SneakyThrows
  @Test
  void testStrUtilClass(){
    Assertions.assertTrue(Class.forName("com.aftership.sdk.lib.StrUtil").newInstance() instanceof  StrUtil);
  }

  @Test
  void isNotBlank() {
    Assertions.assertTrue(StrUtil.isNotBlank("abc"));
    Assertions.assertFalse(StrUtil.isNotBlank(""));
  }

  @Test
  void isBlank() {
    Assertions.assertTrue(StrUtil.isBlank(""));
    Assertions.assertFalse(StrUtil.isBlank("abc"));
    Assertions.assertTrue(StrUtil.isBlank(null));
    Assertions.assertTrue(StrUtil.isBlank(" "));
  }

  @Test
  void uuid4() {
    Assertions.assertEquals(36, StrUtil.uuid4().length());
  }
}
