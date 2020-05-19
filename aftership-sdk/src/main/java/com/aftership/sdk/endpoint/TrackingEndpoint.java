package com.aftership.sdk.endpoint;

import com.aftership.sdk.model.tracking.*;
import com.aftership.sdk.rest.DataEntity;

/** Endpoint provides the interface for all trackings API calls */
public interface TrackingEndpoint {

  /**
   * Create a tracking.
   *
   * @param request CreateTrackingRequest
   * @return DataEntity of SingleTracking
   */
  DataEntity<SingleTracking> createTracking(CreateTrackingRequest request);

  /**
   * Delete a tracking.
   *
   * @param param SingleTrackingParam
   * @return DataEntity of SingleTracking
   */
  DataEntity<SingleTracking> deleteTracking(SingleTrackingParam param);

  /**
   * Get tracking results of multiple trackings.
   *
   * @param param SingleTrackingParam
   * @param optionalParams GetTrackingParams
   * @return DataEntity of SingleTracking
   */
  DataEntity<SingleTracking> getTracking(
      SingleTrackingParam param, GetTrackingParams optionalParams);

  /**
   * GetTrackings Gets tracking results of multiple trackings.
   *
   * @param optionalParams MultiTrackingsParams
   * @return DataEntity of MultiTrackingsData
   */
  DataEntity<MultiTrackingsData> getTrackings(MultiTrackingsParams optionalParams);

  /**
   * UpdateTracking Updates a tracking.
   *
   * @param param SingleTrackingParam
   * @param update UpdateTrackingRequest
   * @return DataEntity of SingleTracking
   */
  DataEntity<SingleTracking> updateTracking(
      SingleTrackingParam param, UpdateTrackingRequest update);

  /**
   * ReTrack an expired tracking once. Max. 3 times per tracking.
   *
   * @param param SingleTrackingParam
   * @return DataEntity of SingleTracking
   */
  DataEntity<SingleTracking> reTrack(SingleTrackingParam param);

  /**
   * Mark a tracking as completed. The tracking won't auto update until retrack it.
   * @param param SingleTrackingParam
   * @param request CompleteTrackingRequest
   * @return DataEntity of SingleTracking
   */
  DataEntity<SingleTracking> completeTracking(SingleTrackingParam param, CompleteTrackingRequest request);
}
