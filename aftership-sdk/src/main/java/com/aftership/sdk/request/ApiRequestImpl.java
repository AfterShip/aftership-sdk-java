package com.aftership.sdk.request;

import com.aftership.sdk.auth.AuthenticationType;
import com.aftership.sdk.auth.SignHeader;
import com.aftership.sdk.auth.SignatureContent;
import com.aftership.sdk.utils.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.*;

import com.aftership.sdk.AfterShip;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.ErrorMessage;
import com.aftership.sdk.exception.ErrorType;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.model.AftershipResponse;
import com.aftership.sdk.model.Meta;
import com.aftership.sdk.model.RateLimit;
import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ApiRequest implementation class
 */
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
   * @param method       Method of http request
   * @param path         path of request url
   * @param requestData  Requested body data
   * @param responseType Type of response
   * @param <T>          Class of request Data
   * @param <R>          Class of response type
   * @return ResponseEntity
   */
  @Override
  public <T, R> AftershipResponse<R> makeRequest(
    HttpMethod method,
    String path,
    Map<String, String> queryParams,
    T requestData,
    Class<R> responseType)
    throws RequestException, ApiException {
    // parameter check
    if (StrUtils.isBlank(path)) {
      throw new IllegalArgumentException(ErrorMessage.CONSTRUCTOR_PATH_IS_EMPTY);
    }
    if (responseType == null) {
      throw new IllegalArgumentException(ErrorMessage.CONSTRUCTOR_RESPONSE_TYPE_IS_NULL);
    }

    // build url
    String url = buildUrl(path, queryParams);

    // build headers
    String requestId = StrUtils.uuid4();
    Map<String, String> requestHeaders =
      new HashMap<String, String>() {
        {
          put("Content-Type", JSON.toString());
          put("request-id", requestId);
          put("aftership-agent", "java-sdk-" + Define.VERSION);
        }
      };

    // build request
    RequestBody requestBody = null;
    String reqBodyStr = "";
    if (requestData != null) {
      reqBodyStr = JsonUtils.getGson().toJson(requestData);
      requestBody = RequestBody.create(reqBodyStr, JSON);
    }

    AuthenticationType authenticationType = app.getAuthenticationType();
    if (authenticationType == AuthenticationType.AES) {
      requestHeaders.put("as-api-key", app.getApiKey());
      // add sign
      SimpleDateFormat sdf3 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
      sdf3.setTimeZone(TimeZone.getTimeZone("GMT"));
      String date = sdf3.format(new Date());
      SignHeader signHeader = app.getSigner().sign(
        SignatureContent.builder()
          .contentType(JSON.toString())
          .body(reqBodyStr)
          .date(date)
          .method(method.getName())
          .headers(requestHeaders)
          .urlStr(url)
          .build());
      if (signHeader.getSignature() == null) {
        throw new RequestException(
          ErrorType.ConstructorError,
          ErrorMessage.CONSTRUCTOR_SIGNATURE_ERROR,
          entryRequestConfig(method, path),
          entryRequestHeaders(requestHeaders),
          entryRequestData(requestData));
      }
      requestHeaders.put("date", date);
      requestHeaders.put(signHeader.getHeader(), signHeader.getSignature());
    } else {
      requestHeaders.put("aftership-api-key", app.getApiKey());
    }

    Request request =
      new Request.Builder()
        .url(url)
        .method(method.getName(), requestBody)
        .headers(Headers.of(requestHeaders))
        .build();

    // call api
    Call call = app.getClient().newCall(request);
    try (Response response = call.execute()) {
      setRateLimiting(this.app, response);
      String message = response.message();
      String bodyString = response.body() == null ? null : response.body().string();
      if (!response.isSuccessful()) {
        throw new ApiException(
          this.app.getRateLimit(),
          BodyParser.processMeta(bodyString),
          entryRequestConfig(method, path),
          entryRequestHeaders(requestHeaders),
          entryRequestData(requestData),
          entryResponseBody(message, bodyString));
      }

      if (StrUtils.isBlank(bodyString) || "{}".equals(bodyString)) {
        throw new RequestException(
          ErrorType.HandlerError,
          ErrorMessage.HANDLER_RESPONSE_BODY_IS_EMPTY,
          entryRequestConfig(method, path),
          entryRequestHeaders(requestHeaders),
          entryRequestData(requestData));
      }

      JsonElement jsonElement = JsonParser.parseString(bodyString);
      if (!jsonElement.isJsonObject()) {
        throw new RequestException(
          ErrorType.HandlerError,
          ErrorMessage.HANDLER_RESPONSE_BODY_IS_NOT_JSON_OBJECT,
          entryRequestConfig(method, path),
          entryRequestHeaders(requestHeaders),
          entryRequestData(requestData),
          entryResponseBody(message, bodyString));
      }

      AftershipResponse<R> result = processResponse(jsonElement, responseType);
      if (result.getMeta() == null) {
        throw new RequestException(
          ErrorType.HandlerError,
          ErrorMessage.HANDLER_RESPONSE_META_IS_NULL,
          entryRequestConfig(method, path),
          entryRequestHeaders(requestHeaders),
          entryRequestData(requestData),
          entryResponseBody(message, bodyString));
      }
      if (!isSuccessful(result.getMeta().getCode())) {
        throw new ApiException(
          this.app.getRateLimit(),
          result.getMeta(),
          entryRequestConfig(method, path),
          entryRequestHeaders(requestHeaders),
          entryRequestData(requestData),
          entryResponseBody(message, bodyString));
      }

      return result;
    } catch (ApiException | RequestException ex) {
      throw ex;
    } catch (Throwable t) {
      throw new RequestException(
        ErrorType.HandlerError,
        t,
        entryRequestConfig(method, path),
        entryRequestHeaders(requestHeaders),
        entryRequestData(requestData));
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
    if (StrUtils.isNotBlank(response.header(RATE_LIMIT_RESET))) {
      rateLimit.setReset(Long.parseLong(Objects.requireNonNull(response.header(RATE_LIMIT_RESET))));
    }
    if (StrUtils.isNotBlank(response.header(RATE_LIMIT_LIMIT))) {
      rateLimit.setLimit(
        Integer.parseInt(Objects.requireNonNull(response.header(RATE_LIMIT_LIMIT))));
    }
    if (StrUtils.isNotBlank(response.header(RATE_LIMIT_REMAINING))) {
      rateLimit.setRemaining(
        Integer.parseInt(Objects.requireNonNull(response.header(RATE_LIMIT_REMAINING))));
    }

    app.setRateLimit(rateLimit);
  }

  private AbstractMap.SimpleEntry<String, Object> entryRequestConfig(
    HttpMethod method, String path) {
    Map<String, String> map =
      new HashMap<String, String>() {
        {
          put("method", method.getName());
          put("path", path);
        }
      };
    return new AbstractMap.SimpleEntry<>(AftershipException.DEBUG_DATA_KEY_REQUEST_CONFIG, map);
  }

  private AbstractMap.SimpleEntry<String, Object> entryRequestHeaders(
    Map<String, String> requestHeaders) {
    return new AbstractMap.SimpleEntry<>(
      AftershipException.DEBUG_DATA_KEY_REQUEST_HEADERS, requestHeaders);
  }

  private <T> AbstractMap.SimpleEntry<String, Object> entryRequestData(T requestData) {
    return new AbstractMap.SimpleEntry<>(
      AftershipException.DEBUG_DATA_KEY_REQUEST_DATA, requestData);
  }

  private AbstractMap.SimpleEntry<String, Object> entryResponseBody(String message, String bodyString) {
    String tag = AftershipException.DEBUG_DATA_KEY_RESPONSE_BODY;
    if (StrUtils.isNotBlank(message)) {
      return new AbstractMap.SimpleEntry<>(tag, null);
    }
    return new AbstractMap.SimpleEntry<>(tag, bodyString);
  }

  private String buildUrl(String path, Map<String, String> query) {
    String url = app.getEndpoint() + path;

    if (query != null && query.size() > 0) {
      url = UrlUtils.fillPathWithQuery(url, query);
    }

    return url;
  }

  private static boolean isSuccessful(int code) {
    for (int i : Define.SUCCESSFUL_CODE_RANGE) {
      if (i == code) {
        return true;
      }
    }
    return false;
  }

  public static <T> T requireResponseBodyNonNull(T obj) throws RequestException {
    if (obj == null) {
      throw new RequestException(
        ErrorType.HandlerError, ErrorMessage.HANDLER_RESPONSE_BODY_OBJECT_IS_NULL);
    }
    return obj;
  }
}
