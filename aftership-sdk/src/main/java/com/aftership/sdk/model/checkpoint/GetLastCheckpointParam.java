package com.aftership.sdk.model.checkpoint;

import java.util.HashMap;
import java.util.Map;
import com.aftership.sdk.endpoint.StringMap;
import com.aftership.sdk.lib.StrUtil;
import lombok.Data;

/** GetLastCheckpointParam is the additional parameters in getLastCheckpoint */
@Data
public class GetLastCheckpointParam implements StringMap {
  /**
   * List of fields to include in the response. Use comma for multiple values. Fields to
   * include:slug,created_at, checkpoint_time,city,coordinates,country_iso3,
   * country_name,message,state,tag,zip Default: none, Example: city,tag
   */
  private String fields;
  /** Support Chinese to English translation for china-ems and china-post only (Example: en) */
  private String lang;

  /**
   * Generate a Map dictionary.
   *
   * @return StringStringMap
   */
  @Override
  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("fields", this.getFields());
    map.put("lang", this.getLang());
    return map;
  }

  /**
   * Field for 'List of fields to include in the response'
   */
  public static class FieldsKind{
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
        return StrUtil.EMPTY;
      }
      return String.join(",", fields);
    }
  }

}
