package com.aftership.sample.notification;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.SdkException;
import com.aftership.sdk.model.notification.Notification;

/** Sample of getNotification method in NotificationEndpoint */
public class GetNotificationSample {
  public static void main(String[] args) throws SdkException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    getNotification(afterShip);
  }

  public static void getNotification(AfterShip afterShip) {
    System.out.println(EndpointPath.GET_NOTIFICATION);

    String id = "vebix4hfu3sr3kac0epve01n";
    try {
      Notification notification = afterShip.getNotificationEndpoint().getNotification(id);
      System.out.println(notification);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
