package com.aftership.sdk.endpoint.checkpoint;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.TestUtil;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.model.FieldsKind;
import com.aftership.sdk.model.LangKind;
import com.aftership.sdk.model.checkpoint.GetCheckpointParam;
import com.aftership.sdk.model.checkpoint.LastCheckpoint;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.utils.UrlUtils;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class GetLastCheckpointBySlugTest {
  public static MockWebServer server;

  @BeforeAll
  static void setUp() throws IOException {
    server = new MockWebServer();
    server.enqueue(
        TestUtil.createMockResponse()
            .setBody(TestUtil.getJson("endpoint/checkpoint/GetLastCheckpointResult.json")));
    server.start();
  }

  @AfterAll
  static void tearDown() throws IOException {
    server.shutdown();
  }

  @Test
  public void testGetLastCheckpoint()
      throws AftershipException, InterruptedException, URISyntaxException {
    AfterShip afterShip = TestUtil.createAfterShip(server);

    System.out.println(
        ">>>>> getLastCheckpoint(SlugTrackingNumber identifier, GetCheckpointParam optionalParam)");
    GetCheckpointParam optionalParam = new GetCheckpointParam();
    String slug = "fedex";
    String trackingNumber = "111111111111";
    optionalParam.setFields(FieldsKind.combine(FieldsKind.SLUG, FieldsKind.CITY));
    optionalParam.setLang(LangKind.CHINA_POST);
    LastCheckpoint lastCheckpoint =
        afterShip
            .getCheckpointEndpoint()
            .getLastCheckpoint(new SlugTrackingNumber(slug, trackingNumber), optionalParam);

    Assertions.assertNotNull(lastCheckpoint);
    Assertions.assertEquals("fedex", lastCheckpoint.getSlug(), "slug mismatch.");
    Assertions.assertEquals("Deal", lastCheckpoint.getCheckpoint().getCity(), "city mismatch.");

    RecordedRequest recordedRequest = server.takeRequest();
    Assertions.assertEquals("GET", recordedRequest.getMethod(), "Method mismatch.");
    Assertions.assertEquals(
        MessageFormat.format("/v4/last_checkpoint/{0}/{1}", slug, trackingNumber),
        new URI(UrlUtils.decode(recordedRequest.getPath())).getPath(),
        "path mismatch.");

    TestUtil.printResponse(afterShip, lastCheckpoint);
    TestUtil.printRequest(recordedRequest);
  }
}
