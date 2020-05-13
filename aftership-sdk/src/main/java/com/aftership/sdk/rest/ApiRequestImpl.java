package com.aftership.sdk.rest;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.error.AftershipError;
import com.aftership.sdk.error.ErrorMessage;
import com.aftership.sdk.error.ErrorType;
import com.aftership.sdk.lib.Define;
import com.aftership.sdk.lib.JsonUtil;
import com.aftership.sdk.lib.StrUtil;
import com.aftership.sdk.model.AftershipResponse;
import com.aftership.sdk.model.Meta;
import com.aftership.sdk.model.RateLimit;
import okhttp3.*;

public class ApiRequestImpl implements ApiRequest {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final AfterShip app;

    public ApiRequestImpl(AfterShip app) {
        this.app = app;
    }

    //TODO(optimize)
    private String buildUrl(String path) {
        return app.getEndpoint() + path;
    }

    private static boolean isSuccessful(int code) {
        for (int i : Define.SUCCESSFUL_CODE_RANGE) {
            if (i == code) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <T, R> ResponseEntity<R> makeRequest(RequestConfig requestConfig, T requestData, Class<R> responseType) {
        if (requestConfig == null || StrUtil.isBlank(requestConfig.getPath())) {
            return ResponseEntity.makeError(AftershipError.make(
                    ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_INVALID_REQUEST_CONFIG));
        }
        if (StrUtil.isBlank(app.getApiKey())) {
            return ResponseEntity.makeError(AftershipError.make(
                    ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_INVALID_API_KEY));
        }
        if (responseType == null) {
            return ResponseEntity.makeError(AftershipError.make(
                    ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_INVALID_RESPONSE_TYPE));
        }

        //build headers
        String requestId = StrUtil.uuid4();
        Map<String, String> requestHeaders = new HashMap<String, String>() {
            {
                put("aftership-api-key", app.getApiKey());
                put("Content-Type", "application/json");
                put("request-id", requestId);
                put("aftership-agent", "java-sdk-" + Define.VERSION);
            }
        };

        //build request
        RequestBody requestBody = null;
        if (requestData != null) {
            requestBody = RequestBody.create(JsonUtil.create().toJson(requestData), JSON);
        }
        Request request = new Request.Builder()
                .url(buildUrl(requestConfig.getPath()))
                .method(requestConfig.getMethod().getName(), requestBody)
                .headers(Headers.of(requestHeaders))
                .build();

        Call call = HttpClient.getClient().newCall(request);
        try (Response response = call.execute()) {
            //System.out.println("isSuccessful: "+ response.isSuccessful());
            if (!response.isSuccessful()) {
                setRateLimiting(this.app, response);
                return ResponseEntity.makeError(AftershipError.make(response.body().string(),
                        entryRequestConfig(requestConfig),
                        entryRequestHeaders(requestHeaders),
                        entryRequestData(requestData),
                        entryResponseBody(response))
                );
            }

            setRateLimiting(this.app, response);

            String jsonBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : "{}";
            //System.out.println("jsonBody: " + jsonBody);
            if (StrUtil.isBlank(jsonBody) || "{}".equals(jsonBody)) {
                return ResponseEntity.makeError(AftershipError.make(ErrorType.HandlerError,
                        ErrorMessage.HANDLER_EMPTY_BODY,
                        entryRequestConfig(requestConfig),
                        entryRequestHeaders(requestHeaders),
                        entryRequestData(requestData)));
            }

            //test:
            //System.out.println("test: jsonBody:" + jsonBody);

            JsonElement jsonElement = JsonParser.parseString(jsonBody);
            if (!jsonElement.isJsonObject()) {
                return ResponseEntity.makeError(AftershipError.make(ErrorType.HandlerError,
                        ErrorMessage.HANDLER_BODY_NOT_JSON_OBJECT,
                        entryRequestConfig(requestConfig),
                        entryRequestHeaders(requestHeaders),
                        entryRequestData(requestData),
                        entryResponseBody(response)));
            }

            AftershipResponse<R> result = processResponse(jsonElement, responseType);
            if (result.getMeta() == null || !isSuccessful(result.getMeta().getCode())) {
                return ResponseEntity.makeError(AftershipError.make(result.getMeta(),
                        entryRequestConfig(requestConfig),
                        entryRequestHeaders(requestHeaders),
                        entryRequestData(requestData)));
            }

            return ResponseEntity.makeResponse(result);
        } catch (Throwable t) {
            return ResponseEntity.makeError(AftershipError.make(ErrorType.HandlerError, t,
                    entryRequestConfig(requestConfig),
                    entryRequestHeaders(requestHeaders),
                    entryRequestData(requestData)));
        }
//        finally {
//            if(!call.isCanceled()){
//                call.cancel();
//            }
//        }
    }

    private <T> AftershipResponse<T> processResponse(JsonElement jsonElement, Class<T> responseType) {
        AftershipResponse<T> responseEntity = new AftershipResponse<>();

        // handle meta field
        Meta meta = BodyParser.processMeta(jsonElement.getAsJsonObject());
        if (meta != null) {
            responseEntity.setMeta(meta);
        }

        // handle data field
        T body = BodyParser.processData(jsonElement.getAsJsonObject(), responseType);
        if (body != null) {
            responseEntity.setData(body);
        }

        return responseEntity;
    }

    private void setRateLimiting(AfterShip app, Response response) {
        if (response == null) {
            return;
        }

        RateLimit rateLimit = new RateLimit();
        if (StrUtil.isNotBlank(response.header("x-ratelimit-reset"))) {
            rateLimit.setReset(Long.parseLong(Objects.requireNonNull(response.header("x-ratelimit-reset"))));
        }
        if (StrUtil.isNotBlank(response.header("x-ratelimit-limit"))) {
            rateLimit.setLimit(Integer.parseInt(Objects.requireNonNull(response.header("x-ratelimit-limit"))));
        }
        if (StrUtil.isNotBlank(response.header("x-ratelimit-remaining"))) {
            rateLimit.setRemaining(Integer.parseInt(Objects.requireNonNull(response.header("x-ratelimit-remaining"))));
        }

        app.setRateLimit(rateLimit);
    }

    private AbstractMap.SimpleEntry<String, Object> entryRequestConfig(RequestConfig requestConfig) {
        return new AbstractMap.SimpleEntry<>("requestConfig", requestConfig);
    }

    private AbstractMap.SimpleEntry<String, Object> entryRequestHeaders(Map<String, String> requestHeaders) {
        return new AbstractMap.SimpleEntry<>("requestHeaders", requestHeaders);
    }

    private <T> AbstractMap.SimpleEntry<String, Object> entryRequestData(T requestData) {
        return new AbstractMap.SimpleEntry<>("requestData", requestData);
    }

    private AbstractMap.SimpleEntry<String, Object> entryResponseBody(Response response) {
        String tag = "responseBody";
        try {
            if (StrUtil.isNotBlank(response.message())) {
                return new AbstractMap.SimpleEntry<>(tag, null);
            }
            String jsonBody = Objects.requireNonNull(response.body()).string();
            return new AbstractMap.SimpleEntry<>(tag, jsonBody);
        } catch (IOException e) {
            e.printStackTrace();
            return new AbstractMap.SimpleEntry<>(tag, null);
        }
    }
}
