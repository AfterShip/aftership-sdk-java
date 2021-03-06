package com.aftership.sdk.utils;

import java.util.UUID;

/** String's assistant method. */
public final class StrUtils {

  /** Empty String */
  public static final String EMPTY = "";

  /**
   * Determine if the string is not empty.
   *
   * @param cs CharSequence
   * @return is not empty
   */
  public static boolean isNotBlank(final CharSequence cs) {
    return !isBlank(cs);
  }

  /**
   * Determine whether the string is empty or not.
   *
   * @param cs CharSequence
   * @return is empty or not
   */
  public static boolean isBlank(final CharSequence cs) {
    int strLen;
    if (cs == null || (strLen = cs.length()) == 0) {
      return true;
    }
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(cs.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Generate uuid string
   *
   * @return String
   */
  public static String uuid4() {
    return UUID.randomUUID().toString();
  }
}
