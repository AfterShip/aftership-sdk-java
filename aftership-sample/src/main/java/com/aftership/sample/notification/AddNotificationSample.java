package com.aftership.sample.notification;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.notification.Notification;
import com.aftership.sdk.model.notification.NotificationWrapper;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;

/** Sample of addNotification method in NotificationEndpoint */
public class AddNotificationSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    addNotification(afterShip);
  }

  public static void addNotification(AfterShip afterShip) {
    System.out.println(EndpointPath.ADD_NOTIFICATION);

    Notification notification = new Notification();
    notification.setSmses(new String[] {"+85261236123", "Invalid Mobile Phone Number"});
    NotificationWrapper notificationWrapper = new NotificationWrapper(notification);
    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("wcwy86mie4o17kadedkcw029");

    DataEntity<NotificationWrapper> entity =
        afterShip.getNotificationEndpoint().addNotification(param, notificationWrapper);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
      System.out.println(entity.getError().getCode());
      System.out.println(entity.getError().getMessage());
    } else {
      Notification result = entity.getData().getNotification();
      System.out.println(result);
    }
  }
}
