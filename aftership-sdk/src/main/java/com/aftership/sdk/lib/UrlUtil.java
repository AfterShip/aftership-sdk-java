package com.aftership.sdk.lib;

import com.aftership.sdk.error.AftershipException;
import com.aftership.sdk.error.ErrorMessage;
import com.aftership.sdk.model.tracking.SingleTrackingParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public final class UrlUtil {
    public static final String UTF8 = "UTF-8";

    public static String encode(String value) {
        return encode(value, UTF8);
    }

    public static String encode(String value, String charset) {
        if (StrUtil.isBlank(value))
            return StrUtil.EMPTY;
        if (StrUtil.isBlank(charset))
            charset = UTF8;
        try {
            return URLEncoder.encode(value, charset);
        } catch (UnsupportedEncodingException e) {
            return StrUtil.EMPTY;
        }
    }

    public static String buildTrackingPath(String id, String slug, String trackingNumber, Map<String, String> query, String rootPath, String action) {
        if (StrUtil.isBlank(rootPath)) {
            throw new AftershipException(ErrorMessage.ConstructorPathIsEmpty);
        }

        String trackingUrl = "";
        if (StrUtil.isNotBlank(id)) {
            trackingUrl = MessageFormat.format("{0}/{1}",
                    rootPath,
                    encode(id));
        } else if (StrUtil.isNotBlank(slug) && StrUtil.isNotBlank(trackingNumber)) {
            trackingUrl = MessageFormat.format("{0}/{1}/{2}",
                    rootPath,
                    encode(slug),
                    encode(trackingNumber));
        }

        if (StrUtil.isNotBlank(action)) {
            trackingUrl = MessageFormat.format("{0}/{1}", trackingUrl,
                    encode(action));
        }

        if (query != null && query.size() > 0) {
            trackingUrl = fillWithQueryString(trackingUrl, query);
        }

        return trackingUrl;
    }

    private static String fillWithQueryString(String path, Map<String, String> query) {
        StringBuilder builder = new StringBuilder(path);
        if (query != null && query.size() > 0) {
            if (!path.endsWith("?")) {
                builder.append("?");
            }
            for (Map.Entry<String, String> entry : query.entrySet()) {
                if (StrUtil.isNotBlank(entry.getValue())) {
                    builder.append(MessageFormat.format("{0}={1}",
                            entry.getKey(),
                            encode(entry.getValue())));
                    builder.append("&");
                }
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

}
