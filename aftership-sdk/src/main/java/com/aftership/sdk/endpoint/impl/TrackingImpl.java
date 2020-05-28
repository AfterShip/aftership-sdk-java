package com.aftership.sdk.endpoint.impl;

import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.TrackingEndpoint;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.exception.InvalidRequestException;
import com.aftership.sdk.model.tracking.CompletedStatus;
import com.aftership.sdk.model.tracking.GetTrackingParams;
import com.aftership.sdk.model.tracking.GetTrackingsParams;
import com.aftership.sdk.model.tracking.NewTracking;
import com.aftership.sdk.model.tracking.NewTrackingRequest;
import com.aftership.sdk.model.tracking.PagedTrackings;
import com.aftership.sdk.model.tracking.SingleTracking;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.model.tracking.Tracking;
import com.aftership.sdk.model.tracking.UpdateTracking;
import com.aftership.sdk.model.tracking.UpdateTrackingRequest;
import com.aftership.sdk.request.ApiRequest;
import com.aftership.sdk.request.HttpMethod;
import com.aftership.sdk.request.ResponseEntity;
import com.aftership.sdk.utils.UrlUtils;

/** TrackingEndpoint's implementation class */
public class TrackingImpl extends AfterShipEndpoint implements TrackingEndpoint {

  /**
   * Constructor
   *
   * @param request ApiRequest object
   */
  public TrackingImpl(ApiRequest request) {
    super(request);
  }

  @Override
  public Tracking createTracking(NewTracking newTracking)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkNullParam(newTracking);
    checkTrackingNumber(newTracking.getTrackingNumber());

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(
            HttpMethod.POST,
            EndpointPath.CREATE_TRACKING,
            null,
            new NewTrackingRequest(newTracking),
            SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public Tracking deleteTracking(String id)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkTrackingSlug(id);

    String path = UrlUtils.buildTrackingPath(id, null, null, EndpointPath.DELETE_TRACKING, null);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(HttpMethod.DELETE, path, null, null, SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public Tracking deleteTracking(SlugTrackingNumber identifier)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        UrlUtils.buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.DELETE_TRACKING,
            null);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(
            HttpMethod.DELETE,
            path,
            this.merge(identifier.getOptionalParams()),
            null,
            SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public Tracking getTracking(String id, GetTrackingParams optionalParams)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkTrackingId(id);

    String path = UrlUtils.buildTrackingPath(id, null, null, EndpointPath.GET_TRACKING, null);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(
            HttpMethod.GET, path, merge(optionalParams), null, SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public Tracking getTracking(SlugTrackingNumber identifier, GetTrackingParams optionalParams)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        UrlUtils.buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.GET_TRACKING,
            null);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(
            HttpMethod.GET,
            path,
            this.merge(optionalParams, identifier.getOptionalParams()),
            null,
            SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public PagedTrackings getTrackings(GetTrackingsParams params)
      throws ConstructorException, InvalidRequestException, ApiException {

    String path = UrlUtils.buildTrackingPath(null, null, null, EndpointPath.GET_TRACKINGS, null);

    ResponseEntity<PagedTrackings> entity =
        this.request.makeRequest(HttpMethod.GET, path, params.toMap(), null, PagedTrackings.class);

    return extractData(entity);
  }

  @Override
  public Tracking updateTracking(String id, UpdateTracking update)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkTrackingId(id);

    String path = UrlUtils.buildTrackingPath(id, null, null, EndpointPath.UPDATE_TRACKING, null);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(
            HttpMethod.PUT, path, null, new UpdateTrackingRequest(update), SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public Tracking updateTracking(SlugTrackingNumber identifier, UpdateTracking update)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        UrlUtils.buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.UPDATE_TRACKING,
            null);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(
            HttpMethod.PUT,
            path,
            this.merge(identifier.getOptionalParams()),
            new UpdateTrackingRequest(update),
            SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public Tracking reTrack(String id)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkTrackingId(id);

    String path =
        UrlUtils.buildTrackingPath(
            id, null, null, EndpointPath.UPDATE_RETRACK, EndpointPath.UPDATE_RETRACK_ACTION);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(HttpMethod.POST, path, null, new Object(), SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public Tracking reTrack(SlugTrackingNumber identifier)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        UrlUtils.buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.UPDATE_RETRACK,
            EndpointPath.UPDATE_RETRACK_ACTION);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            this.merge(identifier.getOptionalParams()),
            new Object(),
            SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public Tracking markAsCompleted(String id, CompletedStatus status)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkTrackingId(id);

    String path =
        UrlUtils.buildTrackingPath(
            id, null, null, EndpointPath.MARK_AS_COMPLETED, EndpointPath.MARK_AS_COMPLETED_ACTION);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(HttpMethod.POST, path, null, status, SingleTracking.class);

    return extractData(entity).getTracking();
  }

  @Override
  public Tracking markAsCompleted(SlugTrackingNumber identifier, CompletedStatus status)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        UrlUtils.buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.MARK_AS_COMPLETED,
            EndpointPath.MARK_AS_COMPLETED_ACTION);

    ResponseEntity<SingleTracking> entity =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            this.merge(identifier.getOptionalParams()),
            status,
            SingleTracking.class);

    return extractData(entity).getTracking();
  }
}
