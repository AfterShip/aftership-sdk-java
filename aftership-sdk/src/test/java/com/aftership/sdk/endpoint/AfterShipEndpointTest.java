package com.aftership.sdk.endpoint;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.aftership.sdk.endpoint.impl.TrackingImpl;
import com.aftership.sdk.error.ErrorMessage;
import com.aftership.sdk.exception.SdkException;

class AfterShipEndpointTest {

  @Test
  void checkTrackingId() throws SdkException {
    TrackingImpl tracking = new TrackingImpl(null);

    try {
      tracking.checkTrackingId("");
    } catch (SdkException e) {
      Assertions.assertTrue(
          e.getMessage().startsWith(ErrorMessage.CONSTRUCTOR_REQUIRED_TRACKING_ID));
    }
  }

  @Test
  void checkTrackingSlug() {
    TrackingImpl tracking = new TrackingImpl(null);

    try {
      tracking.checkTrackingSlug("");
    } catch (SdkException e) {
      Assertions.assertTrue(
          e.getMessage().startsWith(ErrorMessage.CONSTRUCTOR_REQUIRED_SLUG));
    }
  }

  @Test
  void checkTrackingNumber() {
    TrackingImpl tracking = new TrackingImpl(null);

    try {
      tracking.checkTrackingNumber("");
    } catch (SdkException e) {
      Assertions.assertTrue(
          e.getMessage().startsWith(ErrorMessage.CONSTRUCTOR_REQUIRED_TRACKING_NUMBER));
    }
  }

  @Test
  void checkNullParam() {
    TrackingImpl tracking = new TrackingImpl(null);

    try {
      tracking.checkNullParam(null);
    } catch (SdkException e) {
      Assertions.assertTrue(
          e.getMessage().startsWith(ErrorMessage.CONSTRUCTOR_PARAM_IS_NULL));
    }
  }

  @Test
  void merge() {
    TrackingImpl tracking = new TrackingImpl(null);
    Assertions.assertEquals(0, tracking.merge().size());
  }
}
