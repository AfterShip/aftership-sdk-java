package com.aftership.sdk.utils;

/** Define some constants */
public final class Define {

  /** Version of the current component */
  public static final String VERSION = "3.0.0";

  /** The range of http status codes for successful API requests */
  public static final int[] SUCCESSFUL_CODE_RANGE;

  static {
    SUCCESSFUL_CODE_RANGE = new int[100];
    for (int i = 0; i < 100; i++) {
      SUCCESSFUL_CODE_RANGE[i] = 200 + i;
    }
  }
}
