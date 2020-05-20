package com.aftership.sdk.config;

import java.util.Map;
import com.aftership.sdk.lib.StrUtil;

/** Get configuration through environment variables */
public final class EnvGetter {

  /**
   * Get information on current environmental variables
   *
   * @return Map<String, String>
   */
  private static Map<String, String> getEnv() {
    Map<String, String> env = System.getenv();
    return env;
  }

  /**
   * Get the String configuration from the specified environment variable.
   *
   * @param key Specified environmental variable
   * @param defaultValue default value
   * @return value
   */
  public static String getString(String key, String defaultValue) {
    String value = getEnv().get(key);
    if (StrUtil.isBlank(value)) {
      return defaultValue;
    }
    return value;
  }

  /**
   * Get the int configuration from the specified environment variable.
   *
   * @param key Specified environmental variable
   * @param defaultValue default value
   * @return value
   */
  public static int getInt(String key, int defaultValue) {
    String value = getEnv().get(key);
    if (StrUtil.isBlank(value)) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(value);

    } catch (NumberFormatException ex) {
      return defaultValue;
    }
  }

  /**
   * Get the long configuration from the specified environment variable.
   *
   * @param key Specified environmental variable
   * @param defaultValue default value
   * @return value
   */
  public static long getLong(String key, long defaultValue) {
    String value = getEnv().get(key);
    if (StrUtil.isBlank(value)) {
      return defaultValue;
    }

    try {
      return Long.parseLong(value);

    } catch (NumberFormatException ex) {
      return defaultValue;
    }
  }

  /**
   * Get the boolean configuration from the specified environment variable.
   *
   * @param key Specified environmental variable
   * @param defaultValue default value
   * @return value
   */
  public static boolean getBool(String key, boolean defaultValue) {
    String value = getEnv().get(key);
    if (StrUtil.isBlank(value)) {
      return defaultValue;
    }

    try {
      return Boolean.parseBoolean(value);

    } catch (NumberFormatException ex) {
      return defaultValue;
    }
  }
}
