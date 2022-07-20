package com.aftership.sdk.endpoint;

import java.util.List;

import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.estimateddeliverydate.EstimatedDeliveryDate;
import com.aftership.sdk.model.estimateddeliverydate.EstimatedDeliveryDateList;

/**
 * Endpoint provides the interface for all EstimatedDeliveryDate API calls
 */
public interface EstimatedDeliveryDateEndpoint {
  EstimatedDeliveryDateList batchPredictEstimatedDeliveryDate(List<EstimatedDeliveryDate> estimatedDeliveryDates)
    throws SdkException, RequestException, ApiException;
}