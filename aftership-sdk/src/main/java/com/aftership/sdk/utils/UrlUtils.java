package com.aftership.sdk.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Map;
import com.aftership.sdk.error.ErrorMessage;
import com.aftership.sdk.error.ErrorType;
import com.aftership.sdk.exception.SdkException;

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

  public static String buildTrackingPath(
      String id, String slug, String trackingNumber, String rootPath, String action)
      throws SdkException {
    if (StrUtils.isBlank(rootPath)) {
      throw new SdkException(ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_REQUIRED_PATH);
    }

    String trackingUrl = rootPath;

    if (StrUtils.isNotBlank(id)) {
      trackingUrl = MessageFormat.format("{0}/{1}", rootPath, encode(id));
    } else if (StrUtils.isNotBlank(slug) && StrUtils.isNotBlank(trackingNumber)) {
      trackingUrl =
          MessageFormat.format("{0}/{1}/{2}", rootPath, encode(slug), encode(trackingNumber));
    }

    if (StrUtils.isNotBlank(action)) {
      trackingUrl = MessageFormat.format("{0}/{1}", trackingUrl, encode(action));
    }

    return trackingUrl;
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
