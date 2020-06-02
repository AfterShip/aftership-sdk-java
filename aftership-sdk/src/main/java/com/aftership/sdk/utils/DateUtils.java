package com.aftership.sdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/** Date's assistant method. */
public class DateUtils {
  public static final String FORMAT_WITH_T = "yyyy-MM-dd'T'HH:mm:ss";
  public static final String FORMAT_WITH_Z = "yyyy-MM-dd'T'HH:mm:ssZ";
  public static final String FORMAT_WITH_X = "yyyy-MM-dd'T'HH:mm:ssXXX";
  public static final String FORMAT_WITHOUT_T = "yyyy-MM-dd HH:mm:ss";
  public static final String FORMAT_MILLISECONDS = "yyyy-MM-dd HH:mm:ss,SSS";

  /**
   * Convert to date type according to date format
   *
   * @param dateFormat date format
   * @param dateString date string
   * @return Optional of Date
   */
  public static Optional<Date> parse(String dateFormat, String dateString) {
    SimpleDateFormat format = new SimpleDateFormat(dateFormat);
    try {
      Date date = format.parse(dateString);
      return Optional.of(date);
    } catch (ParseException e) {
      return Optional.empty();
    }
  }

  /**
   * Convert Date to a string according to date format
   *
   * @param dateFormat date format
   * @param date Date object
   * @return date string
   */
  public static String format(String dateFormat, Date date) {
    if (date == null) {
      return StrUtils.EMPTY;
    }
    SimpleDateFormat format = new SimpleDateFormat(dateFormat);
    return format.format(date);
  }
}
