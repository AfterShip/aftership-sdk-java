package com.aftership.sdk.endpoint;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import com.aftership.sdk.error.AftershipError;
import com.aftership.sdk.error.ErrorMessage;
import com.aftership.sdk.error.ErrorType;
import com.aftership.sdk.utils.StrUtils;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.ApiRequest;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.rest.ResponseEntity;

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

  /**
   * SingleTrackingParam Error Checking
   *
   * @param param SingleTrackingParam object
   * @param <T> Entity class
   * @return checking result
   */
  protected <T> Map.Entry<Boolean, DataEntity<T>> errorOfSingleTrackingParam(
      SingleTrackingParam param) {
    if (param == null
        || (StrUtils.isBlank(param.getId())
            && StrUtils.isBlank(param.getSlug())
            && StrUtils.isBlank(param.getTrackingNumber()))) {
      return new AbstractMap.SimpleEntry<>(
          true,
          ResponseEntity.makeError(
              AftershipError.make(
                  ErrorType.ConstructorError, ErrorMessage.CONSTRUCTOR_REQUIRED_TRACKING_ID)));
    }
    return new AbstractMap.SimpleEntry<>(false, null);
  }

  /**
   * Merge multiple StringMap interfaces into a HashMap
   *
   * @param items Merge multiple StringMap interfaces
   * @return Map
   */
  protected Map<String, String> merge(StringMap... items) {
    if (items == null || items.length == 0) {
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
