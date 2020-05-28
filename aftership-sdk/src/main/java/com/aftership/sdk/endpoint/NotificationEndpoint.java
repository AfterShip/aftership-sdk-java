package com.aftership.sdk.endpoint;

import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.exception.InvalidRequestException;
import com.aftership.sdk.model.notification.Notification;
import com.aftership.sdk.model.tracking.SlugTrackingNumber;

/** Endpoint provides the interface for all notifications handling API calls */
public interface NotificationEndpoint {

  /**
   * Get contact information for the users to notify when the tracking changes. Please note that
   * only customer receivers will be returned. Any email, sms or webhook that belongs to the Store
   * will not be returned.
   *
   * @param id id of a tracking
   * @return Notification
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Notification getNotification(String id)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Get contact information for the users to notify when the tracking changes. Please note that
   * only customer receivers will be returned. Any email, sms or webhook that belongs to the Store
   * will not be returned.
   *
   * @param identifier SlugTrackingNumber
   * @return Notification
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Notification getNotification(SlugTrackingNumber identifier)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Add notification receivers to a tracking number
   *
   * @param id id for a tracking
   * @param notification Notification
   * @return Notification
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Notification addNotification(String id, Notification notification)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Add notification receivers to a tracking number
   *
   * @param identifier identifier for a tracking
   * @param notification Notification
   * @return Notification
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Notification addNotification(SlugTrackingNumber identifier, Notification notification)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Remove notification receivers from a tracking number
   * @param id id for a tracking
   * @param notification Notification
   * @return Notification
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Notification removeNotification(String id, Notification notification)
      throws ConstructorException, InvalidRequestException, ApiException;

  /**
   * Remove notification receivers from a tracking number
   * @param identifier identifier for a tracking
   * @param notification Notification
   * @return Notification
   * @throws ConstructorException
   * @throws InvalidRequestException
   * @throws ApiException
   */
  Notification removeNotification(SlugTrackingNumber identifier, Notification notification)
      throws ConstructorException, InvalidRequestException, ApiException;
}
