package com.aftership.sdk.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;

/** Url's assistant method. */
public final class UrlUtils {

  /** Url coding */
  public static final String UTF8 = "UTF-8";

  /**
   * encode Url string
   *
   * @param value Url string
   * @return encoded strings
   */
  public static String encode(String value) {
    return encode(value, UTF8);
  }

  public static String encode(String value, String charset) {
    if (StrUtils.isBlank(value)) {
      return StrUtils.EMPTY;
    }
    if (StrUtils.isBlank(charset)) {
      charset = UTF8;
    }
    try {
      return URLEncoder.encode(value, charset);
    } catch (UnsupportedEncodingException e) {
      return StrUtils.EMPTY;
    }
  }

  public static String decode(String value) {
    return decode(value, UTF8);
  }

  public static String decode(String value, String charset) {
    if (StrUtils.isBlank(value)) {
      return StrUtils.EMPTY;
    }
    if (StrUtils.isBlank(charset)) {
      charset = UTF8;
    }
    try {
      return URLDecoder.decode(value, charset);
    } catch (UnsupportedEncodingException e) {
      return StrUtils.EMPTY;
    }
  }

  public static String fillPathWithQuery(String path, Map<String, String> query) {
    StringBuilder builder = new StringBuilder(path);
    if (query != null && query.size() > 0) {
      if (!path.endsWith("?")) {
        builder.append("?");
      }
      for (Map.Entry<String, String> entry : query.entrySet()) {
        if (StrUtils.isNotBlank(entry.getValue())) {
          builder.append(MessageFormat.format("{0}={1}", entry.getKey(), encode(entry.getValue())));
          builder.append("&");
        }
      }
      builder.deleteCharAt(builder.length() - 1);
    }
    return builder.toString();
  }
}
