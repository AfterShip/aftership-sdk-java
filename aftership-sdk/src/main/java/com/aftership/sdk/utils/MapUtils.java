package com.aftership.sdk.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapUtils {

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

//  public static Map.Entry<String, Object>[] toEntries(Map<String, Object> map){
//    List<Map.Entry<String, Object>> list = new ArrayList<>();
//    list.addAll(map.entrySet());
//
//  }

}
