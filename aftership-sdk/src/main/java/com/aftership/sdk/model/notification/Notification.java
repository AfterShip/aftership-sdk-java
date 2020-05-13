package com.aftership.sdk.model.notification;

import lombok.Data;

/**
 * Hash describes the notification information.
 */
@Data
public class Notification {
    /**
     * Email address(es) to receive email notifications.
     */
    private String[] emails;
    /**
     * Phone number(s) to receive sms notifications.
     */
    private String[] smses;
}
