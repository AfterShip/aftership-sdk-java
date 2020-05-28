package com.aftership.sample.notification;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.endpoint.impl.EndpointPath;
import com.aftership.sdk.exception.AftershipException;
import com.aftership.sdk.exception.ConstructorException;
import com.aftership.sdk.model.notification.Notification;

/** Sample of removeNotification method in NotificationEndpoint */
public class RemoveNotificationSample {
  public static void main(String[] args) throws ConstructorException {
    AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
    removeNotification(afterShip);
  }

  public static void removeNotification(AfterShip afterShip) {
    System.out.println(EndpointPath.REMOVE_NOTIFICATION);

    String id = "vebix4hfu3sr3kac0epve01n";
    Notification removeNotification = new Notification();
    removeNotification.setEmails(new String[] {"invalid EMail @ Gmail. com"});
    removeNotification.setSmses(new String[] {"+85261236123"});
    try {
      Notification notification =
          afterShip.getNotificationEndpoint().removeNotification(id, removeNotification);
      System.out.println(notification);
    } catch (AftershipException e) {
      System.out.println(e.getMessage());
    }
  }
}
