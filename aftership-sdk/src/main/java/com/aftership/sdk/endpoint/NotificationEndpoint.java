package com.aftership.sdk.endpoint;

import com.aftership.sdk.model.notification.NotificationWrapper;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;

/**
 * Endpoint provides the interface for all notifications handling API calls
 */
public interface NotificationEndpoint {
    /**
     * Get contact information for the users to notify when the tracking changes.
     * Please note that only customer receivers will be returned.
     * Any email, sms or webhook that belongs to the Store will not be returned.
     * @param param SingleTrackingParam
     * @return DataEntity<NotificationWrapper>
     */
    DataEntity<NotificationWrapper> getNotification(SingleTrackingParam param);

    /**
     * Add notification receivers to a tracking number.
     * @param param SingleTrackingParam
     * @param notificationWrapper NotificationWrapper
     * @return DataEntity<NotificationWrapper>
     */
    DataEntity<NotificationWrapper> addNotification(SingleTrackingParam param, NotificationWrapper notificationWrapper);

    /**
     * Remove notification receivers from a tracking number.
     * @param param SingleTrackingParam
     * @return DataEntity<NotificationWrapper>
     */
    DataEntity<NotificationWrapper> removeNotification(SingleTrackingParam param);
}
