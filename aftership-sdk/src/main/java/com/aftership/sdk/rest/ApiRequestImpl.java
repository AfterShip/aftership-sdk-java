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

/** ApiRequest implementation class */
public class ApiRequestImpl implements ApiRequest {
  private static final String RATE_LIMIT_RESET = "x-ratelimit-reset";
  private static final String RATE_LIMIT_LIMIT = "x-ratelimit-limit";
  private static final String RATE_LIMIT_REMAINING = "x-ratelimit-remaining";
  private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
  private final AfterShip app;

  /**
   * Constructor
   *
   * @param app object of AfterShip
   */
  public ApiRequestImpl(AfterShip app) {
    this.app = app;
  }

  /**
   * Initiate a request
   *
   * @param requestConfig Basic configuration of the request
   * @param requestData Requested body data
   * @param responseType Type of response
   * @param <T> Class of request Data
   * @param <R> Class of response type
   * @return ResponseEntity
   */
  @Override
  public <T, R> ResponseEntity<R> makeRequest(
      RequestConfig requestConfig, T requestData, Class<R> responseType) {
    // parameter check
    if (requestConfig == null || StrUtil.isBlank(requestConfig.getPath())) {
      return ResponseEntity.makeError(
          AftershipError.make(
              ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_INVALID_REQUEST_CONFIG));
    }
    if (StrUtil.isBlank(app.getApiKey())) {
      return ResponseEntity.makeError(
          AftershipError.make(
              ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_INVALID_API_KEY));
    }
    if (responseType == null) {
      return ResponseEntity.makeError(
          AftershipError.make(
              ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_INVALID_RESPONSE_TYPE));
    }

    // build headers
    String requestId = StrUtil.uuid4();
    Map<String, String> requestHeaders =
        new HashMap<String, String>() {
          {
            put("aftership-api-key", app.getApiKey());
            put("Content-Type", "application/json");
            put("request-id", requestId);
            put("aftership-agent", "java-sdk-" + Define.VERSION);
          }
        };

    // build request
    RequestBody requestBody = null;
    if (requestData != null) {
      requestBody = RequestBody.create(JsonUtil.create().toJson(requestData), JSON);
    }
    Request request =
        new Request.Builder()
            .url(buildUrl(requestConfig.getPath()))
            .method(requestConfig.getMethod().getName(), requestBody)
            .headers(Headers.of(requestHeaders))
            .build();

    // call api
    Call call = HttpClient.getClient().newCall(request);
    try (Response response = call.execute()) {
      // System.out.println("isSuccessful: "+ response.isSuccessful());
      if (!response.isSuccessful()) {
        setRateLimiting(this.app, response);
        return ResponseEntity.makeError(
            AftershipError.make(
                response.body().string(),
                entryRequestConfig(requestConfig),
                entryRequestHeaders(requestHeaders),
                entryRequestData(requestData),
                entryResponseBody(response)));
      }

      setRateLimiting(this.app, response);

      if (response.body() == null) {
        return ResponseEntity.makeError(
            AftershipError.make(
                ErrorType.HandlerError,
                ErrorMessage.HANDLER_BODY_IS_NULL,
                entryRequestConfig(requestConfig),
                entryRequestHeaders(requestHeaders)));
      }

      String jsonBody =
          response.body() != null ? Objects.requireNonNull(response.body()).string() : "{}";
      if (StrUtil.isBlank(jsonBody) || "{}".equals(jsonBody)) {
        return ResponseEntity.makeError(
            AftershipError.make(
                ErrorType.HandlerError,
                ErrorMessage.HANDLER_EMPTY_BODY,
                entryRequestConfig(requestConfig),
                entryRequestHeaders(requestHeaders),
                entryRequestData(requestData)));
      }

      JsonElement jsonElement = JsonParser.parseString(jsonBody);
      if (!jsonElement.isJsonObject()) {
        return ResponseEntity.makeError(
            AftershipError.make(
                ErrorType.HandlerError,
                ErrorMessage.HANDLER_BODY_NOT_JSON_OBJECT,
                entryRequestConfig(requestConfig),
                entryRequestHeaders(requestHeaders),
                entryRequestData(requestData),
                entryResponseBody(response)));
      }

      AftershipResponse<R> result = processResponse(jsonElement, responseType);
      if (result.getMeta() == null || !isSuccessful(result.getMeta().getCode())) {
        return ResponseEntity.makeError(
            AftershipError.make(
                result.getMeta(),
                entryRequestConfig(requestConfig),
                entryRequestHeaders(requestHeaders),
                entryRequestData(requestData)));
      }

      return ResponseEntity.makeResponse(result);
    } catch (Throwable t) {
      return ResponseEntity.makeError(
          AftershipError.make(
              ErrorType.HandlerError,
              t,
              entryRequestConfig(requestConfig),
              entryRequestHeaders(requestHeaders),
              entryRequestData(requestData)));
    }
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
    if (StrUtil.isNotBlank(response.header(RATE_LIMIT_RESET))) {
      rateLimit.setReset(Long.parseLong(Objects.requireNonNull(response.header(RATE_LIMIT_RESET))));
    }
    if (StrUtil.isNotBlank(response.header(RATE_LIMIT_LIMIT))) {
      rateLimit.setLimit(
          Integer.parseInt(Objects.requireNonNull(response.header(RATE_LIMIT_LIMIT))));
    }
    if (StrUtil.isNotBlank(response.header(RATE_LIMIT_REMAINING))) {
      rateLimit.setRemaining(
          Integer.parseInt(Objects.requireNonNull(response.header(RATE_LIMIT_REMAINING))));
    }

    app.setRateLimit(rateLimit);
  }

  private AbstractMap.SimpleEntry<String, Object> entryRequestConfig(RequestConfig requestConfig) {
    return new AbstractMap.SimpleEntry<>(AftershipError.DEBUG_KEY_REQUEST_CONFIG, requestConfig);
  }

  private AbstractMap.SimpleEntry<String, Object> entryRequestHeaders(
      Map<String, String> requestHeaders) {
    return new AbstractMap.SimpleEntry<>(AftershipError.DEBUG_KEY_REQUEST_HEADERS, requestHeaders);
  }

  private <T> AbstractMap.SimpleEntry<String, Object> entryRequestData(T requestData) {
    return new AbstractMap.SimpleEntry<>(AftershipError.DEBUG_KEY_REQUEST_DATA, requestData);
  }

  private AbstractMap.SimpleEntry<String, Object> entryResponseBody(Response response) {
    String tag = AftershipError.DEBUG_KEY_RESPONSE_BODY;
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
}
