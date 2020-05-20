package com.aftership.sample.notification;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.notification.Notification;
import com.aftership.sdk.model.notification.NotificationWrapper;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;

/** Sample of removeNotification method in NotificationEndpoint */
public class RemoveNotificationSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    removeNotification(afterShip);
  }

  public static void removeNotification(AfterShip afterShip) {
    System.out.println(EndpointPath.REMOVE_NOTIFICATION);

    Notification notification = new Notification();
    notification.setEmails(new String[] {"invalid EMail @ Gmail. com"});
    notification.setSmses(new String[] {"+85261236123"});
    NotificationWrapper removedNotification = new NotificationWrapper(notification);
    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("wcwy86mie4o17kadedkcw029");

    DataEntity<NotificationWrapper> entity =
        afterShip.getNotificationEndpoint().removeNotification(param, removedNotification);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      Notification result = entity.getData().getNotification();
      System.out.println(result);
    }
  }
}
