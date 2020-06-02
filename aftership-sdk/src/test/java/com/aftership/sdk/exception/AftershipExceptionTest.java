package com.aftership.sdk.exception;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import com.aftership.sdk.error.ErrorType;

class AftershipExceptionTest {

  @Test
  void testConstructor() {
    AftershipException exception =
        new AftershipException(
            ErrorType.HandlerError.getName(), new RuntimeException(), new HashMap<>());

    Assertions.assertNotNull(exception);
  }

  @Test
  void isTooManyRequests() {
    Assertions.assertFalse(
        (new AftershipException(
                ErrorType.HandlerError.getName(), new RuntimeException(), new HashMap<>()))
            .isTooManyRequests());

    Assertions.assertTrue(
        (new AftershipException(
                "TooManyRequests",
                "You have exceeded the API call rate limit. Default limit is 10 requests per second.",
                429,
                new HashMap<>()))
            .isTooManyRequests());

    Assertions.assertFalse(
        (new AftershipException("Unauthorized", "Invalid API Key.", 401, new HashMap<>()))
            .isTooManyRequests());
  }

  @Test
  void isApiError() {
    Assertions.assertFalse(
        (new AftershipException(
                ErrorType.HandlerError.getName(), new RuntimeException(), new HashMap<>()))
            .isApiError());

    Assertions.assertTrue(
        (new AftershipException("Unauthorized", "Invalid API Key.", 401, new HashMap<>()))
            .isApiError());

    Assertions.assertFalse(
        (new AftershipException("-1", "-1", -1, new HashMap<>()))
            .isApiError());
  }

  @Test
  void prettyMessage() {
    AftershipException aftershipException =
        new AftershipException("Unauthorized", "Invalid API Key.", 401, new HashMap<>());
    Assertions.assertNotNull(aftershipException.prettyMessage());
  }

  @Test
  void getMessage() {
    AftershipException aftershipException =
        new AftershipException("", "Invalid API Key.", null, null);

    System.out.println(aftershipException.getMessage());
    Assertions.assertNotNull(aftershipException.getMessage());
  }
}
