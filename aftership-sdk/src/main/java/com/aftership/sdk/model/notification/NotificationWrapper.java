package com.aftership.sdk.model.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

/** Wrapper of Notification */
@Data
@AllArgsConstructor
public class NotificationWrapper {
  /** Hash describes the notification information. */
  private Notification notification;
}
