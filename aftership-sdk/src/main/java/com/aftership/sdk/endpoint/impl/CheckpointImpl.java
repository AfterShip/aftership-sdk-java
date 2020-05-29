package com.aftership.sdk.endpoint.impl;

import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.CheckpointEndpoint;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.checkpoint.GetCheckpointParam;
import com.aftership.sdk.model.checkpoint.LastCheckpoint;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.request.ApiRequest;
import com.aftership.sdk.request.HttpMethod;
import com.aftership.sdk.request.ResponseEntity;
import com.aftership.sdk.utils.UrlUtils;

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

  @Override
  public LastCheckpoint getLastCheckpoint(String id, GetCheckpointParam optionalParam)
      throws SdkException, RequestException, ApiException {
    checkTrackingId(id);

    String path =
        UrlUtils.buildTrackingPath(id, null, null, EndpointPath.GET_LAST_CHECKPOINT, null);

    ResponseEntity<LastCheckpoint> entity =
        this.request.makeRequest(
            HttpMethod.GET, path, this.merge(optionalParam), null, LastCheckpoint.class);

    return extractData(entity);
  }

  @Override
  public LastCheckpoint getLastCheckpoint(
      SlugTrackingNumber identifier, GetCheckpointParam optionalParam)
      throws SdkException, RequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        UrlUtils.buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.GET_LAST_CHECKPOINT,
            null);

    ResponseEntity<LastCheckpoint> entity =
        this.request.makeRequest(
            HttpMethod.GET, path, this.merge(optionalParam), null, LastCheckpoint.class);

    return extractData(entity);
  }
}
