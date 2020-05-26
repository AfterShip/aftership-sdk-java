package com.aftership.sdk.endpoint.impl;

import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.CourierEndpoint;
import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
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

  /**
   * Return a list of couriers activated at your AfterShip account.
   *
   * @return DataEntity of CourierList
   */
  @Override
  public DataEntity<CourierList> listCouriers() {
    return this.request.makeRequest(
        new RequestConfig(HttpMethod.GET, EndpointPath.LIST_COURIERS), null, CourierList.class);
  }

  /**
   * Return a list of all couriers.
   *
   * @return DataEntity of CourierList
   */
  @Override
  public DataEntity<CourierList> listAllCouriers() {
    return this.request.makeRequest(
        new RequestConfig(HttpMethod.GET, EndpointPath.LIST_ALL_COURIERS), null, CourierList.class);
  }

  /**
   * Return a list of matched couriers based on tracking number format and selected couriers or a
   * list of couriers.
   *
   * @param requestData CourierDetectRequest
   * @return DataEntity of CourierDetectList
   */
  @Override
  public DataEntity<CourierDetectList> detectCouriers(CourierDetectRequest requestData) {
    return this.request.makeRequest(
        new RequestConfig(HttpMethod.POST, EndpointPath.DETECT_COURIERS),
        requestData,
        CourierDetectList.class);
  }
}
