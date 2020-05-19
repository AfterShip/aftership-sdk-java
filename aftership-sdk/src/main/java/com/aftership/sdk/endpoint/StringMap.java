package com.aftership.sdk.endpoint;

import java.util.Map;

/** Output the class field as a dictionary description. */
public interface StringMap {
  /**
   * Output as dictionary
   *
   * @return Map
   */
  Map<String, String> toMap();
}
