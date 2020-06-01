package com.aftership.sdk.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.Optional;
import lombok.SneakyThrows;

class DateUtilsTest {

  @SneakyThrows
  @Test
  void classTest() {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.utils.DateUtils").newInstance() instanceof DateUtils);
  }

  @Test
  void parse() {
    Assertions.assertEquals(
        Optional.empty(), DateUtils.parse(DateUtils.FORMAT_WITHOUT_T, "2020-06-01 sss"));
  }

  @Test
  void format() {
    Assertions.assertEquals(
        "2020-06-01 16:06:44", DateUtils.format(DateUtils.FORMAT_WITHOUT_T, new Date(1590998804815L)));
  }
}
