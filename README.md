# aftership-sdk-java

[![Maven Central](https://img.shields.io/maven-central/v/com.slack.api/slack-api-client.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.github.chenjunbiao%20a:aftership-sdk)
[![AfterShip SDKs channel](https://aftership-sdk-slackin.herokuapp.com/badge.svg)](https://aftership-sdk-slackin.herokuapp.com/)
[![Build Status](https://api.travis-ci.org/chenjunbiao/aftership-sdk-java.svg?branch=v2)](https://travis-ci.org/github/chenjunbiao/aftership-sdk-java)

The Java SDK of `AfterShip API`, please see API documentation in: https://www.aftership.com/docs/api/4

Requirements:

- JDK 1.8 or superior.
- Lombok Plugin `for source code build by IDE` . Example: [lombok-intellij-plugin](https://github.com/mplushnikov/lombok-intellij-plugin) 


## Installation

### Maven

```text
<dependency>
  <groupId>com.aftership</groupId>
  <artifactId>aftership-sdk</artifactId>
  <version>2.0.0</version>
</dependency>
```

### Gradle

```text
implementation "com.aftership:aftership-sdk:2.0.0"
```


## Quick Examples

### Main Steps

The following code example shows the `three main steps` to use aftership-sdk-java:

1. Create `AfterShip` Object.

```java
AfterShip afterShip = new AfterShip("YOUR_API_KEY", 
	new AftershipOption("https://api.aftership.com/v4"));
```

2. Get the Endpoint Interface and call the method, then return the `DataEntity<T>` object.

```java 
DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listCouriers();
```

3. Handling `AftershipError` or Business `Data` or `RateLimit`

```java
DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listCouriers();
  if (entity.hasError()) {
    // handle Error.
  	System.out.println(entity.getError().getType());
  } else {
    // handle Data.
  	System.out.println(entity.getData());
}
// handle Rate Limiter.
System.out.println(afterShip.getRateLimit().getReset());
System.out.println(afterShip.getRateLimit().getLimit());
System.out.println(afterShip.getRateLimit().getRemaining());
```

### Error Handling

Error object of this SDK contain fields:

- `type` - **Require** - type of the error, please handle each error by this field

  If it's Aftership's API Error, get code to confirm the cause of the error:

  ```java
  if (entity.getError().isApiError()) {
    System.out.println(entity.getError().getCode());
  }
  ```

- `message` - **Optional** - detail message of the error

- `code` - **Optional** - error code for API Error

  You can find tips for Aftership's error codes in here: https://docs.aftership.com/api/4/errors

- `data` - **Optional** - data lead to the error

  The debug Data is a `Map<String, Object>` object that can get the call parameters. 

  The following data may be available:

  ```java 
  System.out.println(entity.getError().getData().get("requestConfig"));
  System.out.println(entity.getError().getData().get("requestHeaders"));
  System.out.println(entity.getError().getData().get("requestData"));
  System.out.println(entity.getError().getData().get("responseBody"));
  // or
  entity.getError().getData().printData();
  // or
  entity.getError().getData().printData(s -> {});
  ```

### Rate Limiter

To understand AfterShip rate limit policy, please see `limit` session in https://www.aftership.com/docs/api/4

You can get the recent rate limit by `afterShip.getRateLimit()`. Initially all value are `null`.

```java 
AfterShip afterShip = new AfterShip(SampleUtil.getApiKey(), SampleUtil.getAftershipOption());
System.out.println(afterShip.getRateLimit().getReset());
System.out.println(afterShip.getRateLimit().getLimit());
System.out.println(afterShip.getRateLimit().getRemaining());
```

After making an API call, it will be set.

```java
DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listCouriers();
// handle Rate Limiter.
System.out.println(afterShip.getRateLimit().getReset());
System.out.println(afterShip.getRateLimit().getLimit());
System.out.println(afterShip.getRateLimit().getRemaining());
// 1589869159
// 10
// 9
```

## Examples

### /couriers

- #### listCouriers [GET /couriers]

```java 
DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listCouriers();
if (entity.hasError()) {
  System.out.println(entity.getError().getType());
} else {
  System.out.println(entity.getData().getTotal());
  System.out.println(entity.getData().getCouriers());
}
```

- #### listAllCouriers [GET /couriers/all]

```java 
DataEntity<CourierList> entity = afterShip.getCourierEndpoint().listAllCouriers();
if (entity.hasError()) {
	System.out.println(entity.getError().getType());
} else {
	System.out.println(entity.getData().getTotal());
	System.out.println(entity.getData().getCouriers());
}
```

- #### detectCouriers [POST /couriers/detect]

```java
CourierDetectTracking tracking = new CourierDetectTracking();
tracking.setTrackingNumber("906587618687");
CourierDetectRequest courierDetectRequest = new CourierDetectRequest(tracking);

DataEntity<CourierDetectList> entity =
  afterShip.getCourierEndpoint().detectCouriers(courierDetectRequest);
if (entity.hasError()) {
  System.out.println(entity.getError().getType());
} else {
  System.out.println(entity.getData().getTotal());
  System.out.println(entity.getData().getCouriers());
}
```

### /trackings

- #### createTracking [POST /trackings]

```java
// build request object
NewTracking newTracking = new NewTracking();
newTracking.setSlug(new String[] {"dhl"});
newTracking.setTrackingNumber("123456789");
newTracking.setTitle("Title Name");
newTracking.setSmses(new String[] {"+18555072509", "+18555072501"});
newTracking.setEmails(new String[] {
  "email@yourdomain.com", "another_email@yourdomain.com"});
newTracking.setOrderId("ID 1234");
newTracking.setOrderIdPath("http://www.aftership.com/order_id=1234");
newTracking.setCustomFields(
  new HashMap<String, String>(2) {
    {
      put("product_name", "iPhone Case");
      put("product_price", "USD19.99");
    }
  });
newTracking.setLanguage("en");
newTracking.setOrderPromisedDeliveryDate("2019-05-20");
newTracking.setDeliveryType("pickup_at_store");
newTracking.setPickupLocation("Flagship Store");
newTracking.setPickupNote(
  "Reach out to our staffs when you arrive our stores for shipment pickup");

CreateTrackingRequest request = new CreateTrackingRequest(newTracking);

DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().createTracking(request);
if (entity.hasError()) {
  System.out.println(entity.getError().getType());
} else {
  System.out.println(entity.getData().getTracking());
}
```



- #### deleteTracking [DELETE /trackings/:slug/:tracking_number]

```java 
SingleTrackingParam param = new SingleTrackingParam();
param.setId("abc");

DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().deleteTracking(param);
if (entity.hasError()) {
  System.out.println(entity.getError().getType());
} else {
  Tracking tracking = entity.getData().getTracking();
  System.out.println(tracking);
}
```

- #### getTrackings [GET /trackings]

```java 
MultiTrackingsParams optionalParams = new MultiTrackingsParams();
optionalParams.setFields(FieldsKind.combine(FieldsKind.ORDER_ID, FieldsKind.TAG));
optionalParams.setLimit(2);

DataEntity<MultiTrackingsData> entity =
  afterShip.getTrackingEndpoint().getTrackings(optionalParams);
if (entity.hasError()) {
  System.out.println(entity.getError().getType());
} else {
  List<Tracking> trackings = entity.getData().getTrackings();
  System.out.println("size: " + trackings.size());
  System.out.println(trackings);
}
```

- #### getTracking [GET /trackings/:slug/:tracking_number]

  - Get by id:

    ```java
    SingleTrackingParam param = new SingleTrackingParam();
    param.setId("vebix4hfu3sr3kac0epve01n");
    
    DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint()
      .getTracking(param, null);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println(tracking);
      if (tracking != null) {
        System.out.println(tracking.getSlug());
        System.out.println(tracking.getTrackingNumber());
      }
    }
    ```

  - Get by slug and tracking_number:

    ```java 
    SingleTrackingParam param = new SingleTrackingParam();
    param.setSlug("dhl");
    param.setTrackingNumber("1234567890");
    
    DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint()
      .getTracking(param, null);
    if (entity.hasError()) {
      System.out.println(entity.getError().getType());
    } else {
      Tracking tracking = entity.getData().getTracking();
      System.out.println(tracking);
      if (tracking != null) {
        System.out.println(tracking.getId());
      }
    }
    ```

- #### updateTracking [PUT /trackings/:slug/:tracking_number]

```java
SingleTrackingParam param = new SingleTrackingParam();
param.setId("vebix4hfu3sr3kac0epve01n");

UpdateTracking updateTracking = new UpdateTracking();
updateTracking.setTitle("title123");
UpdateTrackingRequest request = new UpdateTrackingRequest();
request.setTracking(updateTracking);

DataEntity<SingleTracking> entity =
    afterShip.getTrackingEndpoint().updateTracking(param, request);
if (entity.hasError()) {
  System.out.println(entity.getError().getType());
} else {
  Tracking tracking = entity.getData().getTracking();
  System.out.println(tracking);
  if (tracking != null) {
    System.out.println(tracking.getTitle());
  }
}
```

- #### reTrack [POST /trackings/:slug/:tracking_number/retrack]

```java
  SingleTrackingParam param = new SingleTrackingParam();
  param.setId("vebix4hfu3sr3kac0epve01n");

  DataEntity<SingleTracking> entity = afterShip.getTrackingEndpoint().reTrack(param);
  if (entity.hasError()) {
    System.out.println(entity.getError().getType());
    System.out.println(entity.getError().getCode());
    System.out.println(entity.getError().getMessage());
  } else {
    Tracking tracking = entity.getData().getTracking();
    System.out.println(tracking);
    if (tracking != null) {
      System.out.println(tracking.isActive());
    }
  }
}
```

- #### markAsCompleted [POST /trackings/:slug/:tracking_number/mark-as-completed]

```java 
SingleTrackingParam param = new SingleTrackingParam();
param.setId("wcwy86mie4o17kadedkcw029");
MarkAsCompletedRequest request = new MarkAsCompletedRequest(ReasonKind.LOST);

DataEntity<SingleTracking> entity =
  afterShip.getTrackingEndpoint().markAsCompleted(param, request);
if (entity.hasError()) {
  System.out.println(entity.getError().getType());
} else {
  Tracking tracking = entity.getData().getTracking();
  System.out.println(tracking);
}
```

### /last_checkpoint

- #### getLastCheckpoint [GET /last_checkpoint/:slug/:tracking_number]

```java
SingleTrackingParam param = new SingleTrackingParam();
param.setId("wcwy86mie4o17kadedkcw029");
GetLastCheckpointParam optionalParams = new GetLastCheckpointParam();
optionalParams.setFields(FieldsKind.combine(FieldsKind.TAG, FieldsKind.ORDER_ID));
optionalParams.setLang(LangKind.CHINA_EMS);

DataEntity<LastCheckpoint> entity =
    afterShip.getCheckpointEndpoint().getLastCheckpoint(param, optionalParams);
if (entity.hasError()) {
  System.out.println(entity.getError().getType());
} else {
  LastCheckpoint lastCheckpoint = entity.getData();
  System.out.println(lastCheckpoint.getSlug());
  System.out.println(lastCheckpoint.getTrackingNumber());
  System.out.println(lastCheckpoint.getCheckpoint());
}
```

### /notifications

- #### getNotification [GET /notifications/:slug/:tracking_number]

```java 
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
```

- #### addNotification [POST /notifications/:slug/:tracking_number/add]

```java
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
// Notification(emails=[another_email@yourdomain.com, email@yourdomain.com], smses=[+85291239123, +85261236123])
```

- #### removeNotification [POST /notifications/:slug/:tracking_number/remove]

```java 
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
// Notification(emails=[email@yourdomain.com, another_email@yourdomain.com], smses=[+85291239123])
```



## Notes

- #### When specifying a tracking, the `id` is equivalent to the `slug and tracking_number`.

```java 
SingleTrackingParam param = new SingleTrackingParam();
param.setId("vebix4hfu3sr3kac0epve01n");
```

```java 
SingleTrackingParam param = new SingleTrackingParam();
param.setSlug("dhl");
param.setTrackingNumber("1234567890");
```



## Release History

#### 2020-05-20-v2.0.0

- Highly encapsulated interface, supports rapid construction of new interfaces.
- Complete parsing of the message body, Support IntelliSense.
- Use Gradle as a dependency manager.
  - depend okhttp library
  - depend gson library
- Simplify object definition with Lombok.
- Published in maven central repository.
- The interfaces for implementation and refactoring are as follows:
  - CourierEndpoint.listCouriers
  - CourierEndpoint.listAllCouriers
  - CourierEndpoint.detectCouriers
  - TrackingEndpoint.createTracking
  - TrackingEndpoint.deleteTracking
  - TrackingEndpoint.getTracking
  - TrackingEndpoint.getTrackings
  - TrackingEndpoint.updateTracking
  - TrackingEndpoint.reTrack
  - TrackingEndpoint.markAsCompleted
  - CheckpointEndpoint.getLastCheckpoint
  - NotificationEndpoint.getNotification
  - NotificationEndpoint.addNotification
  - NotificationEndpoint.removeNotification

#### 2016-04-26-v1.2.0

* Properties added in Checkpoint class
 1. slug
 2. location

#### 2016-02-02-v1.1.1
* Solving issue at Checkpoint.java, typo in ```country_iso3```

## License
Copyright (c) 2015-2020 Aftership  
Licensed under the MIT license.

​	

