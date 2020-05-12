package com.aftership.sdk;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.rest.ResponseEntity;
import okhttp3.mockwebserver.MockResponse;
import okio.BufferedSource;
import okio.Okio;


public class TestUtil {
    private static final long ResetValue = System.currentTimeMillis();
    private static final int LimitValue = 1000;
    private static final AtomicInteger RemainingValue = new AtomicInteger(LimitValue);

    public static final String ENDPOINT_VERSION = "v4";
    public static final String ENDPOINT_FORMAT = "http://localhost:%s/" + ENDPOINT_VERSION;
    public static final String YOUR_API_KEY = "YOUR_API_KEY";

    public static String getRequestPath(String subPath) {
        return String.format("/%s%s", ENDPOINT_VERSION, subPath);
    }

    public static String getJson(String path) throws IOException {
        //URL res = JavaClass.class.getClassLoader().getResource(path);
        URL res = ClassLoader.getSystemResource(path);
        assert res != null;
        try (BufferedSource bufferedSource = Okio.buffer(Okio.source(new File(res.getPath())))) {
            return bufferedSource.readByteString().string(StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static MockResponse createMockResponse() {
        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("Cache-Control", "no-cache");
        setLimit(mockResponse);
        return mockResponse;
    }

    private static void setLimit(MockResponse response) {
        response.addHeader("x-ratelimit-reset", TestUtil.ResetValue);
        response.addHeader("x-ratelimit-limit", TestUtil.LimitValue);
        response.addHeader("x-ratelimit-remaining", TestUtil.RemainingValue.decrementAndGet());
    }

    public static <T> void printResponse(AfterShip afterShip, DataEntity<T> entity) {
        if (entity.hasError()) {
            System.out.println(entity.getError());
        } else {
            System.out.println("test Entity: " + entity.getData().getClass().getName());
            System.out.println(entity.getData());
            System.out.println(((ResponseEntity<T>) entity).getResponse().getMeta());
        }
        System.out.println(afterShip.getRateLimit().toString());
    }

}
