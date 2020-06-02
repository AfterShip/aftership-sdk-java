package com.aftership.sdk.endpoint;

import com.aftership.sdk.exception.ApiException;
import com.aftership.sdk.exception.RequestException;
import com.aftership.sdk.exception.SdkException;
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
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Notification getNotification(String id)
      throws SdkException, RequestException, ApiException;

  /**
   * Get contact information for the users to notify when the tracking changes. Please note that
   * only customer receivers will be returned. Any email, sms or webhook that belongs to the Store
   * will not be returned.
   *
   * @param identifier SlugTrackingNumber
   * @return Notification
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Notification getNotification(SlugTrackingNumber identifier)
      throws SdkException, RequestException, ApiException;

  /**
   * Add notification receivers to a tracking number
   *
   * @param id id for a tracking
   * @param notification Notification
   * @return Notification
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Notification addNotification(String id, Notification notification)
      throws SdkException, RequestException, ApiException;

  /**
   * Add notification receivers to a tracking number
   *
   * @param identifier identifier for a tracking
   * @param notification Notification
   * @return Notification
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Notification addNotification(SlugTrackingNumber identifier, Notification notification)
      throws SdkException, RequestException, ApiException;

  /**
   * Remove notification receivers from a tracking number
   * @param id id for a tracking
   * @param notification Notification
   * @return Notification
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Notification removeNotification(String id, Notification notification)
      throws SdkException, RequestException, ApiException;

  /**
   * Remove notification receivers from a tracking number
   * @param identifier identifier for a tracking
   * @param notification Notification
   * @return Notification
   * @throws SdkException SdkException
   * @throws RequestException RequestException
   * @throws ApiException ApiException
   */
  Notification removeNotification(SlugTrackingNumber identifier, Notification notification)
      throws SdkException, RequestException, ApiException;
}
