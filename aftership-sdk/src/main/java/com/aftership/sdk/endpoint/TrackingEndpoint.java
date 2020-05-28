package com.aftership.sdk.endpoint;

import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.exception.InvalidRequestException;
import com.aftership.sdk.model.tracking.*;

/** Endpoint provides the interface for all trackings API calls */
public interface TrackingEndpoint {

  /**
   * Create a tracking
   * @param newTracking Tracking will be created
   * @return Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking createTracking(NewTracking newTracking)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Delete a tracking
   * @param id id of a tracking
   * @return deleted Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking deleteTracking(String id)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Delete a tracking
   * @param identifier identifier of a tracking
   * @return deleted Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking deleteTracking(SlugTrackingNumber identifier)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Get a tracking
   * @param id id of a tracking
   * @param optionalParams GetTrackingParams
   * @return Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking getTracking(String id, GetTrackingParams optionalParams)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Get a tracking
   * @param identifier identifier of a tracking
   * @param optionalParams GetTrackingParams
   * @return Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking getTracking(SlugTrackingNumber identifier, GetTrackingParams optionalParams)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * GetTrackings Gets tracking results of multiple trackings.
   *
   * @param params GetTrackingsParams
   * @return DataEntity of PagedTrackings
   */
  PagedTrackings getTrackings(GetTrackingsParams params)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Updates a tracking
   * @param id id of a tracking
   * @param update UpdateTrackingParams
   * @return Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking updateTracking(String id, UpdateTracking update)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Updates a tracking
   * @param identifier identifier of a tracking
   * @param update UpdateTrackingParams
   * @return Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking updateTracking(SlugTrackingNumber identifier, UpdateTracking update)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * ReTrack an expired tracking once. Max. 3 times per tracking
   * @param id id of a tracking
   * @return Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking reTrack(String id) throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * ReTrack an expired tracking once. Max. 3 times per tracking
   * @param identifier identifier of a tracking
   * @return Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking reTrack(SlugTrackingNumber identifier)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Mark a tracking as completed. The tracking won't auto update until retrack it
   * @param id id of a tracking
   * @param status CompletedStatus
   * @return Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking markAsCompleted(String id, CompletedStatus status)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Mark a tracking as completed. The tracking won't auto update until retrack it
   * @param identifier identifier of a tracking
   * @param status CompletedStatus
   * @return Tracking
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Tracking markAsCompleted(SlugTrackingNumber identifier, CompletedStatus status)
      throws ConstructorException, InvalidRequestException, ApiException;
}
