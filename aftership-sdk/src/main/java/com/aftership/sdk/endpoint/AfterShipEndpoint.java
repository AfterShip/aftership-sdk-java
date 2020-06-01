package com.aftership.sdk.endpoint;

import java.util.HashMap;
import java.util.Map;
import com.aftership.sdk.error.ErrorMessage;
import com.aftership.sdk.error.ErrorType;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.AftershipResponse;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.request.ApiRequest;
import com.aftership.sdk.utils.StrUtils;

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

  public void checkTrackingId(String id) throws SdkException {
    if (StrUtils.isBlank(id)) {
      throw new SdkException(
          ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_REQUIRED_TRACKING_ID);
    }
  }

  public void checkSlugTrackingNumber(SlugTrackingNumber slugTrackingNumber) throws SdkException {
    checkNullParam(slugTrackingNumber);
    checkTrackingSlug(slugTrackingNumber.getSlug());
    checkTrackingNumber(slugTrackingNumber.getTrackingNumber());
  }

  public void checkTrackingSlug(String slug) throws SdkException {
    if (StrUtils.isBlank(slug)) {
      throw new SdkException(ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_REQUIRED_SLUG);
    }
  }

  public void checkTrackingNumber(String trackingNumber) throws SdkException {
    if (StrUtils.isBlank(trackingNumber)) {
      throw new SdkException(
          ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_REQUIRED_TRACKING_NUMBER);
    }
  }

  public void checkNullParam(Object param) throws SdkException {
    if (param == null) {
      throw new SdkException(ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_PARAM_IS_NULL);
    }
  }

  public <T> T extractData(AftershipResponse<T> response) {
    return response.getData();
  }

  /**
   * Merge multiple StringMap interfaces into a HashMap
   *
   * @param items Merge multiple StringMap interfaces
   * @return Map
   */
  protected Map<String, String> merge(StringMap... items) {
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
}
