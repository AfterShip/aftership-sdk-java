package com.aftership.sdk.endpoint;

import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.tracking.CompletedStatus;
import com.aftership.sdk.model.tracking.GetTrackingParams;
import com.aftership.sdk.model.tracking.GetTrackingsParams;
import com.aftership.sdk.model.tracking.NewTracking;
import com.aftership.sdk.model.tracking.PagedTrackings;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.model.tracking.UpdateTracking;

/** Endpoint provides the interface for all trackings API calls */
public interface TrackingEndpoint {

  /**
   * Create a tracking
   * @param newTracking Tracking will be created
   * @return Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Tracking createTracking(NewTracking newTracking)
      throws SdkException, RequestException, ApiException;

  /**
   * Delete a tracking
   * @param id id of a tracking
   * @return deleted Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Tracking deleteTracking(String id)
      throws SdkException, RequestException, ApiException;

  /**
   * Delete a tracking
   * @param identifier identifier of a tracking
   * @return deleted Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Tracking deleteTracking(SlugTrackingNumber identifier)
      throws SdkException, RequestException, ApiException;

  /**
   * Get a tracking
   * @param id id of a tracking
   * @param optionalParams GetTrackingParams
   * @return Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Tracking getTracking(String id, GetTrackingParams optionalParams)
      throws SdkException, RequestException, ApiException;

  /**
   * Get a tracking
   * @param identifier identifier of a tracking
   * @param optionalParams GetTrackingParams
   * @return Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Tracking getTracking(SlugTrackingNumber identifier, GetTrackingParams optionalParams)
      throws SdkException, RequestException, ApiException;

  /**
   * GetTrackings Gets tracking results of multiple trackings.
   *
   * @param params GetTrackingsParams
   * @return DataEntity of PagedTrackings
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  PagedTrackings getTrackings(GetTrackingsParams params)
      throws SdkException, RequestException, ApiException;

  /**
   * Updates a tracking
   * @param id id of a tracking
   * @param update UpdateTrackingParams
   * @return Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Tracking updateTracking(String id, UpdateTracking update)
      throws SdkException, RequestException, ApiException;

  /**
   * Updates a tracking
   * @param identifier identifier of a tracking
   * @param update UpdateTrackingParams
   * @return Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   * @throws ApiException ApiException
   */
  Tracking updateTracking(SlugTrackingNumber identifier, UpdateTracking update)
      throws SdkException, RequestException, ApiException;

  /**
   * ReTrack an expired tracking once. Max. 3 times per tracking
   * @param id id of a tracking
   * @return Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Tracking reTrack(String id) throws SdkException, RequestException, ApiException;

  /**
   * ReTrack an expired tracking once. Max. 3 times per tracking
   * @param identifier identifier of a tracking
   * @return Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Tracking reTrack(SlugTrackingNumber identifier)
      throws SdkException, RequestException, ApiException;

  /**
   * Mark a tracking as completed. The tracking won't auto update until retrack it
   * @param id id of a tracking
   * @param status CompletedStatus
   * @return Tracking
   * @throws SdkException SDK Exception
   * @throws RequestException Request Exception
   * @throws ApiException Api Exception
   */
  Tracking markAsCompleted(String id, CompletedStatus status)
      throws SdkException, RequestException, ApiException;

  /**
   * Mark a tracking as completed. The tracking won't auto update until retrack it
   * @param identifier identifier of a tracking
   * @param status CompletedStatus
   * @return Tracking
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   * @throws ApiException ApiException
   */
  Tracking markAsCompleted(SlugTrackingNumber identifier, CompletedStatus status)
      throws SdkException, RequestException, ApiException;
}
