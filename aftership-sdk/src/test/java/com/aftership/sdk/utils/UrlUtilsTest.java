package com.aftership.sdk.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import lombok.SneakyThrows;

class UrlUtilsTest {

  @SneakyThrows
  @Test
  void testUrlUtilClass() {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.utils.UrlUtils").newInstance() instanceof UrlUtils);
  }

  @Test
  void encode() {
    Assertions.assertEquals("%25", UrlUtils.encode("%"));
  }

  @Test
  void testEncode() {
    Assertions.assertEquals("%25", UrlUtils.encode("%", UrlUtils.UTF8));
    Assertions.assertEquals("%25", UrlUtils.encode("%", null));
    Assertions.assertEquals(StrUtils.EMPTY, UrlUtils.encode("", UrlUtils.UTF8));
    Assertions.assertEquals(StrUtils.EMPTY, UrlUtils.encode("%", "UTF64"));
  }

  @Test
  void decode() {
    Assertions.assertEquals("%", UrlUtils.decode("%25"));
  }

  @Test
  void testDecode() {
    Assertions.assertEquals("%", UrlUtils.decode("%25", UrlUtils.UTF8));
    Assertions.assertEquals("%", UrlUtils.decode("%25", null));
    Assertions.assertEquals(StrUtils.EMPTY, UrlUtils.decode("", UrlUtils.UTF8));
    Assertions.assertEquals(StrUtils.EMPTY, UrlUtils.decode("%25", "UTF64"));
  }

  @Test
  void buildTrackingPath() {
    Assertions.assertThrows(
        com.aftership.sdk.error.AftershipException.class,
        () -> {
          UrlUtils.buildTrackingPath("id", "slug", "trackingNumber", new HashMap<>(), null, "");
        });

    Assertions.assertEquals(
        "/couriers/slug/trackingNumber",
        UrlUtils.buildTrackingPath(
            "", "slug", "trackingNumber", new HashMap<>(), EndpointPath.COURIERS, ""));

    Assertions.assertEquals(
        StrUtils.EMPTY,
        UrlUtils.buildTrackingPath(
            "", "", "trackingNumber", new HashMap<>(), EndpointPath.COURIERS, ""));
  }

  @Test
  void fillPathWithQuery() {
    Assertions.assertEquals(
        "/trackings", UrlUtils.fillPathWithQuery(EndpointPath.TRACKING, new HashMap<>()));
    Assertions.assertEquals(
        "/trackings?aa=11",
        UrlUtils.fillPathWithQuery(
            EndpointPath.TRACKING,
            new HashMap<String, String>() {
              {
                put("aa", "11");
              }
            }));
    Assertions.assertEquals(
        "/trackings?aa=11",
        UrlUtils.fillPathWithQuery(
            EndpointPath.TRACKING + "?",
            new HashMap<String, String>() {
              {
                put("aa", "11");
              }
            }));
  }
}
