package com.aftership.sdk.error;

import com.aftership.sdk.lib.StrUtil;
import com.aftership.sdk.model.Meta;
import com.aftership.sdk.rest.BodyParser;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.ResponseBody;

import java.util.*;

@Data
@NoArgsConstructor
public class AftershipError {
    private String type = StrUtil.EMPTY;
    private String message = StrUtil.EMPTY;
    private Integer code = null;
    private Map<String, Object> data;

    @SafeVarargs
    public static AftershipError make(ResponseBody responseBody, Map.Entry<String, Object>... data) {
        return make(BodyParser.processMeta(responseBody), data);
    }

    @SafeVarargs
    public static AftershipError make(Meta meta, Map.Entry<String, Object>... data) {
        if (meta != null) {
            return make(meta.getType(), meta.getMessage(), meta.getCode(), data);
        }
        return make(ErrorType.HandlerError, ErrorMessage.HandlerNullMeta);
    }

    @SafeVarargs
    public static AftershipError make(ErrorType errorType, Throwable t, Map.Entry<String, Object>... data) {
        return make(errorType.getName(), t.getMessage(), data);
    }

    @SafeVarargs
    public static AftershipError make(ErrorType errorType, String message, Map.Entry<String, Object>... data) {
        return make(errorType.getName(), message, data);
    }

    @SafeVarargs
    public static AftershipError make(String type, String message, Map.Entry<String, Object>... data) {
        return make(type, message, null, data);
    }

    @SafeVarargs
    public static AftershipError make(String type, String message, Integer code, Map.Entry<String, Object>... data) {
        AftershipError error = new AftershipError();
        if (StrUtil.isNotBlank(type)) {
            error.setType(type);
        }
        if (StrUtil.isNotBlank(message)) {
            error.setMessage(message);
        }
        if (code != null) {
            error.setCode(code);
        }

        if (data != null && data.length > 0) {
            Map<String, Object> map = new HashMap<>();
            for (Map.Entry<String, Object> item : data) {
                if (!map.containsKey(item.getKey())) {
                    map.put(item.getKey(), item.getValue());
                }
            }
            error.setData(map);
        }
        return error;
    }
}
