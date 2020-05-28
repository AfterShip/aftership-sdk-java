package com.aftership.sdk.endpoint.impl;

import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.CourierEndpoint;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.exception.InvalidRequestException;
import com.aftership.sdk.model.checkpoint.LastCheckpoint;
import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
import com.aftership.sdk.model.courier.CourierDetectTracking;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.*;

/** CourierEndpoint's implementation class */
public class CourierImpl extends AfterShipEndpoint implements CourierEndpoint {

  /**
   * Constructor
   *
   * @param request ApiRequest object
   */
  public CourierImpl(ApiRequest request) {
    super(request);
  }

  @Override
  public CourierList listCouriers() throws InvalidRequestException, ApiException {
    ResponseEntity<CourierList> entity =
        this.request.makeRequest(
            HttpMethod.GET, EndpointPath.LIST_COURIERS, null, null, CourierList.class);

    return extractData(entity);
  }

  @Override
  public CourierList listAllCouriers() throws InvalidRequestException, ApiException {
    ResponseEntity<CourierList> entity =
        this.request.makeRequest(
            HttpMethod.GET, EndpointPath.LIST_ALL_COURIERS, null, null, CourierList.class);

    return extractData(entity);
  }

  @Override
  public CourierDetectList detectCouriers(CourierDetectTracking detectTracking)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkNullParam(detectTracking);
    checkTrackingNumber(detectTracking.getTrackingNumber());

    ResponseEntity<CourierDetectList> entity =
        this.request.makeRequest(
            HttpMethod.POST,
            EndpointPath.DETECT_COURIERS,
            null,
            new CourierDetectRequest(detectTracking),
            CourierDetectList.class);

    return extractData(entity);
  }
}
