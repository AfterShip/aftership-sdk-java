package com.aftership.sdk.endpoint.impl;

import java.util.Map;
import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.CheckpointEndpoint;
import com.aftership.sdk.utils.UrlUtils;
import com.aftership.sdk.model.checkpoint.GetLastCheckpointParam;
import com.aftership.sdk.model.checkpoint.LastCheckpoint;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.ApiRequest;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.rest.HttpMethod;
import com.aftership.sdk.rest.RequestConfig;

/** CheckpointEndpoint's implementation class */
public class CheckpointImpl extends AfterShipEndpoint implements CheckpointEndpoint {

  /**
   * Constructor
   *
   * @param request ApiRequest object
   */
  public CheckpointImpl(ApiRequest request) {
    super(request);
  }

  /**
   * getLastCheckpoint Return the tracking information of the last checkpoint of a single tracking.
   *
   * @param param SingleTrackingParam
   * @param optionalParams GetLastCheckpointParam
   * @return DataEntity of LastCheckpoint
   */
  @Override
  public DataEntity<LastCheckpoint> getLastCheckpoint(
      SingleTrackingParam param, GetLastCheckpointParam optionalParams) {
    Map.Entry<Boolean, DataEntity<LastCheckpoint>> error = errorOfSingleTrackingParam(param);
    if (error.getKey()) {
      return error.getValue();
    }

    Map<String, String> query = this.merge(param.getOptionalParams(), optionalParams);

    String path =
        UrlUtils.buildTrackingPath(
            param.getId(),
            param.getSlug(),
            param.getTrackingNumber(),
            query,
            EndpointPath.GET_LAST_CHECKPOINT,
            null);

    return this.request.makeRequest(
        new RequestConfig(HttpMethod.GET, path), null, LastCheckpoint.class);
  }
}
