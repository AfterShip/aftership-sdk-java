package com.aftership.sdk.endpoint.impl;

import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.NotificationEndpoint;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.exception.InvalidRequestException;
import com.aftership.sdk.model.notification.Notification;
import com.aftership.sdk.model.notification.NotificationWrapper;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.request.ApiRequest;
import com.aftership.sdk.request.HttpMethod;
import com.aftership.sdk.request.ResponseEntity;
import com.aftership.sdk.utils.UrlUtils;

/** NotificationEndpoint's implementation class */
public class NotificationImpl extends AfterShipEndpoint implements NotificationEndpoint {

  /**
   * Constructor
   *
   * @param request ApiRequest object
   */
  public NotificationImpl(ApiRequest request) {
    super(request);
  }

  @Override
  public Notification getNotification(String id)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkTrackingId(id);

    String path = UrlUtils.buildTrackingPath(id, null, null, EndpointPath.GET_NOTIFICATION, null);

    ResponseEntity<NotificationWrapper> entity =
        this.request.makeRequest(HttpMethod.GET, path, null, null, NotificationWrapper.class);

    return extractData(entity).getNotification();
  }

  @Override
  public Notification getNotification(SlugTrackingNumber identifier)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        UrlUtils.buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.GET_NOTIFICATION,
            null);

    ResponseEntity<NotificationWrapper> entity =
        this.request.makeRequest(HttpMethod.GET, path, null, null, NotificationWrapper.class);

    return extractData(entity).getNotification();
  }

  @Override
  public Notification addNotification(String id, Notification notification)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkTrackingId(id);

    String path =
        UrlUtils.buildTrackingPath(
            id, null, null, EndpointPath.ADD_NOTIFICATION, EndpointPath.ADD_NOTIFICATION_ACTION);

    ResponseEntity<NotificationWrapper> entity =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            null,
            new NotificationWrapper(notification),
            NotificationWrapper.class);

    return extractData(entity).getNotification();
  }

  @Override
  public Notification addNotification(SlugTrackingNumber identifier, Notification notification)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        UrlUtils.buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.ADD_NOTIFICATION,
            EndpointPath.ADD_NOTIFICATION_ACTION);

    ResponseEntity<NotificationWrapper> entity =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            null,
            new NotificationWrapper(notification),
            NotificationWrapper.class);

    return extractData(entity).getNotification();
  }

  @Override
  public Notification removeNotification(String id, Notification notification)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkTrackingId(id);

    String path =
        UrlUtils.buildTrackingPath(
            id,
            null,
            null,
            EndpointPath.REMOVE_NOTIFICATION,
            EndpointPath.REMOVE_NOTIFICATION_ACTION);

    ResponseEntity<NotificationWrapper> entity =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            null,
            new NotificationWrapper(notification),
            NotificationWrapper.class);

    return extractData(entity).getNotification();
  }

  @Override
  public Notification removeNotification(SlugTrackingNumber identifier, Notification notification)
      throws ConstructorException, InvalidRequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        UrlUtils.buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.REMOVE_NOTIFICATION,
            EndpointPath.REMOVE_NOTIFICATION_ACTION);

    ResponseEntity<NotificationWrapper> entity =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            null,
            new NotificationWrapper(notification),
            NotificationWrapper.class);

    return extractData(entity).getNotification();
  }
}
