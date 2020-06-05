package com.aftership.sdk.endpoint;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import com.aftership.sdk.exception.ErrorMessage;
import com.aftership.sdk.exception.ErrorType;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.AftershipResponse;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.request.ApiRequest;
import com.aftership.sdk.utils.StrUtils;
import com.aftership.sdk.utils.UrlUtils;

/** AfterShip Endpoint's base class */
public abstract class AfterShipEndpoint {
  /** ApiRequest object */
  protected final ApiRequest request;

  /**
   * constructor
   *
   * @param request ApiRequest Object
   */
  public AfterShipEndpoint(ApiRequest request) {
    this.request = request;
  }

  protected void checkTrackingId(String id) throws SdkException {
    if (StrUtils.isBlank(id)) {
      throw new SdkException(
          ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_REQUIRED_TRACKING_ID);
    }
  }

  protected void checkSlugTrackingNumber(SlugTrackingNumber slugTrackingNumber)
      throws SdkException {
    checkNullParam(slugTrackingNumber);
    checkTrackingSlug(slugTrackingNumber.getSlug());
    checkTrackingNumber(slugTrackingNumber.getTrackingNumber());
  }

  protected void checkTrackingSlug(String slug) throws SdkException {
    if (StrUtils.isBlank(slug)) {
      throw new SdkException(ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_REQUIRED_SLUG);
    }
  }

  protected void checkTrackingNumber(String trackingNumber) throws SdkException {
    if (StrUtils.isBlank(trackingNumber)) {
      throw new SdkException(
          ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_REQUIRED_TRACKING_NUMBER);
    }
  }

  protected void checkNullParam(Object param) throws SdkException {
    if (param == null) {
      throw new SdkException(ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_PARAM_IS_NULL);
    }
  }

  protected <T> T extractData(AftershipResponse<T> response) {
    return response.getData();
  }

  /**
   * Merge multiple StringMap interfaces into a HashMap
   *
   * @param items Merge multiple StringMap interfaces
   * @return Map
   */
  protected Map<String, String> mergeMap(StringMap... items) {
    if (items.length == 0) {
      return new HashMap<>();
    }

    Map<String, String> query = new HashMap<>();
    for (StringMap item : items) {
      if (item != null) {
        query.putAll(item.toMap());
      }
    }

    return query;
  }

  /**
   * take map from StringMap interface
   * @param item StringMap
   * @return Map<String, String>
   */
  protected Map<String, String> takeMap(StringMap item){
    if (item == null) {
      return new HashMap<>(0);
    }
    return item.toMap();
  }

  protected String buildTrackingPath(
      String id, String slug, String trackingNumber, String rootPath, String action)
      throws SdkException {
    if (StrUtils.isBlank(rootPath)) {
      throw new SdkException(ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_REQUIRED_PATH);
    }

    String trackingUrl = rootPath;

    if (StrUtils.isNotBlank(id)) {
      trackingUrl = MessageFormat.format("{0}/{1}", rootPath, UrlUtils.encode(id));
    } else if (StrUtils.isNotBlank(slug) && StrUtils.isNotBlank(trackingNumber)) {
      trackingUrl =
          MessageFormat.format(
              "{0}/{1}/{2}", rootPath, UrlUtils.encode(slug), UrlUtils.encode(trackingNumber));
    }

    if (StrUtils.isNotBlank(action)) {
      trackingUrl = MessageFormat.format("{0}/{1}", trackingUrl, UrlUtils.encode(action));
    }

    return trackingUrl;
  }
}
