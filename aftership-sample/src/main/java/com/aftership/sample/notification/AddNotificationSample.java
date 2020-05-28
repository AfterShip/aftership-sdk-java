package com.aftership.sample.notification;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.model.notification.Notification;

/** Sample of addNotification method in NotificationEndpoint */
public class AddNotificationSample {
  public static void main(String[] args) throws ConstructorException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    addNotification(afterShip);
  }

  public static void addNotification(AfterShip afterShip) {
    System.out.println(EndpointPath.ADD_NOTIFICATION);

    String id = "vebix4hfu3sr3kac0epve01n";
    Notification addNotification = new Notification();
    addNotification.setSmses(new String[] {"+85261236123", "Invalid Mobile Phone Number"});

    try {
      Notification notification =
          afterShip.getNotificationEndpoint().addNotification(id, addNotification);
      System.out.println(notification);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
