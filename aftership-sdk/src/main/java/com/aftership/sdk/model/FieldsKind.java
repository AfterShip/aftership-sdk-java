package com.aftership.sdk.model;

import com.aftership.sdk.utils.StrUtils;

/**
 * Field for 'List of fields to include in the response'
 */
public class FieldsKind{
  public static final String SLUG = "slug";
  public static final String CREATED_AT = "created_at";
  public static final String CHECKPOINT_TIME = "checkpoint_time";
  public static final String CITY = "city";
  public static final String COORDINATES = "coordinates";
  public static final String COUNTRY_ISO3 = "country_iso3";
  public static final String COUNTRY_NAME = "country_name";
  public static final String MESSAGE = "message";
  public static final String STATE = "state";
  public static final String TAG = "tag";
  public static final String ZIP = "zip";

  /**
   * Combine to Comma-delimited strings
   *
   * @param fields Array
   * @return Comma-delimited strings
   */
  public static String combine(String... fields) {
    if (fields == null) {
      return StrUtils.EMPTY;
    }
    return String.join(",", fields);
  }
}
