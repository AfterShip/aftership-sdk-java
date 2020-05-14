package com.aftership.sdk.impl;

import java.util.Map;
import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.NotificationEndpoint;
import com.aftership.sdk.lib.UrlUtil;
import com.aftership.sdk.model.notification.NotificationWrapper;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.ApiRequest;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.rest.HttpMethod;
import com.aftership.sdk.rest.RequestConfig;

/**
 * NotificationEndpoint's implementation class
 *
 * @author chenjunbiao
 */
public class NotificationImpl extends AfterShipEndpoint implements NotificationEndpoint {

  /**
   * Constructor
   *
   * @param request ApiRequest object
   */
  public NotificationImpl(ApiRequest request) {
    super(request);
  }

  /**
   * Get contact information for the users to notify when the tracking changes. Please note that
   * only customer receivers will be returned. Any email, sms or webhook that belongs to the Store
   * will not be returned.
   *
   * @param param SingleTrackingParam
   * @return DataEntity<NotificationWrapper>
   */
  @Override
  public DataEntity<NotificationWrapper> getNotification(SingleTrackingParam param) {
    Map.Entry<Boolean, DataEntity<NotificationWrapper>> error = errorOfSingleTrackingParam(param);
    if (error.getKey()) {
      return error.getValue();
    }

    String path =
        UrlUtil.buildTrackingPath(
            param.getId(),
            param.getSlug(),
            param.getTrackingNumber(),
            null,
            EndpointPath.GET_NOTIFICATION,
            null);

    return this.request.makeRequest(
        new RequestConfig(HttpMethod.GET, path), null, NotificationWrapper.class);
  }

  /**
   * Add notification receivers to a tracking number.
   *
   * @param param SingleTrackingParam
   * @param notificationWrapper NotificationWrapper
   * @return DataEntity<NotificationWrapper>
   */
  @Override
  public DataEntity<NotificationWrapper> addNotification(
      SingleTrackingParam param, NotificationWrapper notificationWrapper) {
    Map.Entry<Boolean, DataEntity<NotificationWrapper>> error = errorOfSingleTrackingParam(param);
    if (error.getKey()) {
      return error.getValue();
    }

    String path =
        UrlUtil.buildTrackingPath(
            param.getId(),
            param.getSlug(),
            param.getTrackingNumber(),
            null,
            EndpointPath.ADD_NOTIFICATION,
            EndpointPath.ADD_NOTIFICATION_ACTION);

    return this.request.makeRequest(
        new RequestConfig(HttpMethod.POST, path), notificationWrapper, NotificationWrapper.class);
  }

  /**
   * Remove notification receivers from a tracking number.
   *
   * @param param SingleTrackingParam
   * @return DataEntity<NotificationWrapper>
   */
  @Override
  public DataEntity<NotificationWrapper> removeNotification(SingleTrackingParam param) {
    Map.Entry<Boolean, DataEntity<NotificationWrapper>> error = errorOfSingleTrackingParam(param);
    if (error.getKey()) {
      return error.getValue();
    }

    String path =
        UrlUtil.buildTrackingPath(
            param.getId(),
            param.getSlug(),
            param.getTrackingNumber(),
            null,
            EndpointPath.REMOVE_NOTIFICATION,
            EndpointPath.REMOVE_NOTIFICATION_ACTION);

    // new Object(), fix for 'method POST must have a request body'
    return this.request.makeRequest(
        new RequestConfig(HttpMethod.POST, path), new Object(), NotificationWrapper.class);
  }
}
