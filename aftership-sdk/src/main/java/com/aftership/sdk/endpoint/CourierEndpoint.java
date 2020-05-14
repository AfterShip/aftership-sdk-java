package com.aftership.sdk.endpoint;

import com.aftership.sdk.model.courier.CourierDetectList;
import com.aftership.sdk.model.courier.CourierDetectRequest;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.DataEntity;

/**
 * Endpoint provides the interface for all Courier API calls
 *
 * @author chenjunbiao
 */
public interface CourierEndpoint {
  /**
   * Return a list of couriers activated at your AfterShip account.
   *
   * @return DataEntity<CourierList>
   */
  DataEntity<CourierList> listCouriers();

  /**
   * Return a list of all couriers.
   *
   * @return DataEntity<CourierList>
   */
  DataEntity<CourierList> listAllCouriers();

  /**
   * Return a list of matched couriers based on tracking number format and selected couriers or a
   * list of couriers.
   *
   * @param requestData CourierDetectRequest
   * @return DataEntity<CourierDetectList>
   */
  DataEntity<CourierDetectList> detectCouriers(CourierDetectRequest requestData);
}
