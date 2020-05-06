package com.aftership.sdk.rest;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ApiRequestImpl implements ApiRequest {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final long TIMEOUT = 50 * 1000L;//TODO: set with config
    private static final OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
            .build();
    private final AfterShip app;

    public ApiRequestImpl(AfterShip app) {
        this.app = app;
    }

    //TODO: optimize
    private String buildUrl(String path) {
        return app.getEndpoint() + path;
    }

    @Override
    public <T, R> ResponseEntity<R> makeRequest(RequestConfig requestConfig, T requestData, Class<R> responseType) {
        if (requestConfig == null) {
            return ResponseEntity.makeError(AftershipError.makeSdkError(
                    ErrorType.ConstructorError, "ConstructorError: requestConfig is null"));
        }
        if (StrUtil.isBlank(app.getApiKey())) {
            return ResponseEntity.makeError(AftershipError.makeSdkError(
                    ErrorType.ConstructorError, ErrorMessage.ConstructorInvalidApiKey));
        }

        String requestId = StrUtil.uuid4();
        HashMap<String, String> headers = new HashMap<String, String>() {
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
                .headers(Headers.of(headers))
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            if (!response.isSuccessful() && response.code() != 401) {
                return ResponseEntity.makeError(AftershipError.getApiError(null));
            }

            try {
                setRateLimiting(this.app, response);

                String jsonBody = response.body() != null ? Objects.requireNonNull(response.body()).string() : "{}";
                if (StrUtil.isBlank(jsonBody)) {
                    return ResponseEntity.makeError(AftershipError.makeRequestError(
                            ErrorType.HandlerError, ErrorMessage.HandlerEmptyBody, response));
                }

                AftershipResponse<R> result = processResponse(jsonBody, responseType);
                if (result.getMeta() == null) {
                    return ResponseEntity.makeError(AftershipError.makeRequestError(
                            ErrorType.HandlerError, ErrorMessage.HandlerInvalidBody, response));
                }

                return ResponseEntity.makeResponse(result);
            } catch (Throwable t) {
                return ResponseEntity.makeError(AftershipError.makeRequestError(
                        ErrorType.HandlerError, t, response));
            }

        } catch (IOException e) {
            return ResponseEntity.makeError(AftershipError.makeRequestError(
                    ErrorType.InternalError, e, requestData));
        } catch (Throwable t) {
            return ResponseEntity.makeError(AftershipError.makeRequestError(
                    ErrorType.HandlerError, t, requestData));
        }

    }

    private <T> AftershipResponse<T> processResponse(String json, Class<T> responseType) {
        if (StrUtil.isBlank(json)) {
            return new AftershipResponse<>(null, null);
        }

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        if (jsonObject == null) {
            return new AftershipResponse<>(null, null);
        }

        AftershipResponse<T> responseEntity = new AftershipResponse<>();

        // handle meta field
        JsonElement metaJson = jsonObject.get("meta");
        if(metaJson != null){
            Meta meta = JsonUtil.create().fromJson(metaJson, Meta.class);
            responseEntity.setMeta(meta);
        }

        // handle data field
        JsonElement dataJson = jsonObject.get("data");
        if(dataJson != null){
            T body = JsonUtil.create().fromJson(dataJson, responseType);
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
}
