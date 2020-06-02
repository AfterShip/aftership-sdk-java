package com.aftership.sdk.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.AbstractMap.SimpleEntry;
import lombok.SneakyThrows;

class MapUtilsTest {

  @SneakyThrows
  @Test
  void classTest() {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.utils.MapUtils").newInstance() instanceof MapUtils);
  }

  @Test
  void toMap() {
    Assertions.assertEquals(0, MapUtils.toMap().size());
    Assertions.assertEquals(1, MapUtils.toMap(new SimpleEntry<>("abc", 123)).size());
  }
}
