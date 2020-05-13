package com.aftership.sdk.endpoint;

import com.aftership.sdk.model.notification.SingleNotification;
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
     * @return DataEntity<SingleNotification>
     */
    DataEntity<SingleNotification> getNotification(SingleTrackingParam param);
}
