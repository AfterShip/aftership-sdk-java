package com.aftership.sdk.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import lombok.SneakyThrows;

class EnvGetterTest {

  @SneakyThrows
  @Test
  void classTest() {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.config.EnvGetter").newInstance() instanceof EnvGetter);
  }

  @Test
  void getString() {
    Assertions.assertEquals("abc", EnvGetter.getString("TEST_KEY_STRING", "abc"));
    Assertions.assertEquals("efg", EnvGetter.getString("", "efg"));
  }

  @Test
  void getInt() {
    Assertions.assertEquals(123, EnvGetter.getInt("TEST_KEY_INT", 123));
    Assertions.assertEquals(456, EnvGetter.getInt("", 456));
  }

  @Test
  void getLong() {
    Assertions.assertEquals(123L, EnvGetter.getLong("TEST_KEY_LONG", 123L));
    Assertions.assertEquals(456L, EnvGetter.getLong("", 456L));
  }

  @Test
  void getBool() {
    Assertions.assertEquals(true, EnvGetter.getBool("TEST_KEY_BOOL", true));
    Assertions.assertEquals(true, EnvGetter.getBool("", true));
  }
}
