package com.aftership.sdk.lib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import com.aftership.sdk.impl.EndpointPath;
import lombok.SneakyThrows;

class UrlUtilTest {

  @SneakyThrows
  @Test
  void testUrlUtilClass() {
    Assertions.assertTrue(
        Class.forName("com.aftership.sdk.lib.UrlUtil").newInstance() instanceof UrlUtil);
  }

  @Test
  void encode() {
    Assertions.assertEquals("%25", UrlUtil.encode("%"));
  }

  @Test
  void testEncode() {
    Assertions.assertEquals("%25", UrlUtil.encode("%", UrlUtil.UTF8));
    Assertions.assertEquals("%25", UrlUtil.encode("%", null));
    Assertions.assertEquals(StrUtil.EMPTY, UrlUtil.encode("", UrlUtil.UTF8));
    Assertions.assertEquals(StrUtil.EMPTY, UrlUtil.encode("%", "UTF64"));
  }

  @Test
  void decode() {
    Assertions.assertEquals("%", UrlUtil.decode("%25"));
  }

  @Test
  void testDecode() {
    Assertions.assertEquals("%", UrlUtil.decode("%25", UrlUtil.UTF8));
    Assertions.assertEquals("%", UrlUtil.decode("%25", null));
    Assertions.assertEquals(StrUtil.EMPTY, UrlUtil.decode("", UrlUtil.UTF8));
    Assertions.assertEquals(StrUtil.EMPTY, UrlUtil.decode("%25", "UTF64"));
  }

  @Test
  void buildTrackingPath() {
    Assertions.assertThrows(
        com.aftership.sdk.error.AftershipException.class,
        () -> {
          UrlUtil.buildTrackingPath("id", "slug", "trackingNumber", new HashMap<>(), null, "");
        });

    Assertions.assertEquals(
        "/couriers/slug/trackingNumber",
        UrlUtil.buildTrackingPath(
            "", "slug", "trackingNumber", new HashMap<>(), EndpointPath.COURIERS, ""));

    Assertions.assertEquals(
        StrUtil.EMPTY,
        UrlUtil.buildTrackingPath(
            "", "", "trackingNumber", new HashMap<>(), EndpointPath.COURIERS, ""));
  }

  @Test
  void fillPathWithQuery() {
    Assertions.assertEquals(
        "/trackings", UrlUtil.fillPathWithQuery(EndpointPath.TRACKING, new HashMap<>()));
    Assertions.assertEquals(
        "/trackings?aa=11",
        UrlUtil.fillPathWithQuery(
            EndpointPath.TRACKING,
            new HashMap<String, String>() {
              {
                put("aa", "11");
              }
            }));
    Assertions.assertEquals(
        "/trackings?aa=11",
        UrlUtil.fillPathWithQuery(
            EndpointPath.TRACKING + "?",
            new HashMap<String, String>() {
              {
                put("aa", "11");
              }
            }));
  }
}
