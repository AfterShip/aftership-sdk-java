package com.aftership.sdk.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

  @SafeVarargs
  public static Map<String, Object> toMap(Map.Entry<String, Object>... data){
    if (data != null && data.length > 0) {
      Map<String, Object> map = new HashMap<>(data.length);
      for (Map.Entry<String, Object> item : data) {
        if (!map.containsKey(item.getKey())) {
          map.put(item.getKey(), item.getValue());
        }
      }
      return map;
    } else {
      return new HashMap<>(0);
    }
  }

}
