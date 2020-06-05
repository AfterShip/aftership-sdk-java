package com.aftership.sdk.endpoint.impl;

import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.NotificationEndpoint;
import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.AftershipResponse;
import com.aftership.sdk.model.notification.Notification;
import com.aftership.sdk.model.notification.NotificationWrapper;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;
import com.aftership.sdk.request.ApiRequest;
import com.aftership.sdk.request.HttpMethod;

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
      throws SdkException, RequestException, ApiException {
    checkTrackingId(id);

    String path = buildTrackingPath(id, null, null, EndpointPath.GET_NOTIFICATION, null);

    AftershipResponse<NotificationWrapper> response =
        this.request.makeRequest(HttpMethod.GET, path, null, null, NotificationWrapper.class);

    return extractData(response).getNotification();
  }

  @Override
  public Notification getNotification(SlugTrackingNumber identifier)
      throws SdkException, RequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.GET_NOTIFICATION,
            null);

    AftershipResponse<NotificationWrapper> response =
        this.request.makeRequest(HttpMethod.GET, path, null, null, NotificationWrapper.class);

    return extractData(response).getNotification();
  }

  @Override
  public Notification addNotification(String id, Notification notification)
      throws SdkException, RequestException, ApiException {
    checkTrackingId(id);

    String path =
        buildTrackingPath(
            id, null, null, EndpointPath.ADD_NOTIFICATION, EndpointPath.ADD_NOTIFICATION_ACTION);

    AftershipResponse<NotificationWrapper> response =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            null,
            new NotificationWrapper(notification),
            NotificationWrapper.class);

    return extractData(response).getNotification();
  }

  @Override
  public Notification addNotification(SlugTrackingNumber identifier, Notification notification)
      throws SdkException, RequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.ADD_NOTIFICATION,
            EndpointPath.ADD_NOTIFICATION_ACTION);

    AftershipResponse<NotificationWrapper> response =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            null,
            new NotificationWrapper(notification),
            NotificationWrapper.class);

    return extractData(response).getNotification();
  }

  @Override
  public Notification removeNotification(String id, Notification notification)
      throws SdkException, RequestException, ApiException {
    checkTrackingId(id);

    String path =
        buildTrackingPath(
            id,
            null,
            null,
            EndpointPath.REMOVE_NOTIFICATION,
            EndpointPath.REMOVE_NOTIFICATION_ACTION);

    AftershipResponse<NotificationWrapper> response =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            null,
            new NotificationWrapper(notification),
            NotificationWrapper.class);

    return extractData(response).getNotification();
  }

  @Override
  public Notification removeNotification(SlugTrackingNumber identifier, Notification notification)
      throws SdkException, RequestException, ApiException {
    checkSlugTrackingNumber(identifier);

    String path =
        buildTrackingPath(
            null,
            identifier.getSlug(),
            identifier.getTrackingNumber(),
            EndpointPath.REMOVE_NOTIFICATION,
            EndpointPath.REMOVE_NOTIFICATION_ACTION);

    AftershipResponse<NotificationWrapper> response =
        this.request.makeRequest(
            HttpMethod.POST,
            path,
            null,
            new NotificationWrapper(notification),
            NotificationWrapper.class);

    return extractData(response).getNotification();
  }
}
