package com.aftership.sdk.endpoint;

import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectTracking;
import com.aftership.sdk.model.courier.CourierList;

/** Endpoint provides the interface for all Courier API calls */
public interface CourierEndpoint {
  /**
   * Return a list of couriers activated at your AfterShip account.
   *
   * @return CourierList
   * @throws RequestException
   * @throws ApiException
   */
  CourierList listCouriers() throws RequestException, ApiException, SdkException;

  /**
   * Return a list of all couriers.
   *
   * @return CourierList
   * @throws RequestException
   * @throws ApiException
   */
  CourierList listAllCouriers() throws RequestException, ApiException, SdkException;

  /**
   * Return a list of matched couriers based on tracking number format and selected couriers or a
   * list of couriers.
   *
   * @param detectTracking CourierDetectTracking
   * @return CourierDetectList
   * @throws SdkException
   * @throws RequestException
   * @throws ApiException
   */
  CourierDetectList detectCouriers(CourierDetectTracking detectTracking)
      throws SdkException, RequestException, ApiException;
}
