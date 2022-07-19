package com.aftership.sdk.endpoint.impl;

import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.EstimatedDeliveryDateEndpoint;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.AftershipResponse;
import com.aftership.sdk.model.estimateddeliverydate.EstimatedDeliveryDate;
import com.aftership.sdk.model.estimateddeliverydate.EstimatedDeliveryDateBatchPredictRequest;
import com.aftership.sdk.model.estimateddeliverydate.EstimatedDeliveryDateList;
import com.aftership.sdk.request.ApiRequest;
import com.aftership.sdk.request.HttpMethod;

import java.util.List;

/** EstimatedDeliveryDateEndpoint's implementation class */
public class EstimatedDeliveryDateImpl extends AfterShipEndpoint implements EstimatedDeliveryDateEndpoint {

  /**
   * Constructor
   *
   * @param request ApiRequest object
   */
  public EstimatedDeliveryDateImpl(ApiRequest request) {
    super(request);
  }

  @Override
  public EstimatedDeliveryDateList batchPredictEstimatedDeliveryDate(List<EstimatedDeliveryDate> estimatedDeliveryDates)
    throws SdkException, RequestException, ApiException {
    checkNullParam(estimatedDeliveryDates);
    checkEmptyCollection(estimatedDeliveryDates);

    AftershipResponse<EstimatedDeliveryDateList> response =
      this.request.makeRequest(
        HttpMethod.POST,
        EndpointPath.BATCH_PREDICT_ESTIMATED_DELIVERY_DATE,
        null,
        new EstimatedDeliveryDateBatchPredictRequest(estimatedDeliveryDates),
        EstimatedDeliveryDateList.class);

    return extractData(response);
  }
}
