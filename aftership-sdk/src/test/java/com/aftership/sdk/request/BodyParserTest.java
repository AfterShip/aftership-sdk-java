package com.aftership.sdk.request;

import static org.junit.jupiter.api.Assertions.assertNull;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.aftership.sdk.model.courier.Courier;
import lombok.SneakyThrows;

class BodyParserTest {

  @SneakyThrows
  @Test
  void classTest() {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.request.BodyParser").newInstance() instanceof BodyParser);
  }

  @Test
  void processMeta() {
    assertNull(BodyParser.processMeta(""));
  }

  @Test
  void processData() {
    Assertions.assertNull(BodyParser.processData(new JsonObject(), Courier.class));
  }
}