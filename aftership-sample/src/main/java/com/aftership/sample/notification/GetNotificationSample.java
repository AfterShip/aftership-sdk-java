package com.aftership.sample.notification;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.impl.EndpointPath;
import com.aftership.sdk.model.notification.Notification;
import com.aftership.sdk.model.notification.NotificationWrapper;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.DataEntity;

/** Sample of getNotification method in NotificationEndpoint */
public class GetNotificationSample {
  public static void main(String[] args) {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    getNotification(afterShip);
  }

  public static void getNotification(AfterShip afterShip) {
    System.out.println(EndpointPath.GET_NOTIFICATION);

    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("wcwy86mie4o17kadedkcw029");
    DataEntity<NotificationWrapper> entity =
        afterShip.getNotificationEndpoint().getNotification(param);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      Notification notification = entity.getData().getNotification();
      System.out.println(notification);
    }
  }
}
