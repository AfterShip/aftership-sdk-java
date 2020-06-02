package com.aftership.sdk.exception;

import java.util.Map;
import com.aftership.sdk.model.Meta;
import com.aftership.sdk.model.RateLimit;
import com.aftership.sdk.utils.MapUtils;
import lombok.Getter;

/** Exception for API Server */
public class ApiException extends AftershipException {

  @Getter private final RateLimit rateLimit;

  /**
   * Constructor
   *
   * @param rateLimit You are limited to 10 reqs / sec per account. Practically, this means you
   *     should (when possible) authenticate users so that limits are well outside the reach of a
   *     given user
   * @param meta The meta key is used to communicate extra information about the response to the
   *     developer
   * @param data Debug information of error
   */
  @SafeVarargs
  public ApiException(RateLimit rateLimit, Meta meta, Map.Entry<String, Object>... data) {
    super(meta.getType(), meta.getMessage(), meta.getCode(), MapUtils.toMap(data));
    this.rateLimit = rateLimit;
  }
}
