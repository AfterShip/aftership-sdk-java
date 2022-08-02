package com.aftership.sample.auth;

import com.aftership.sample.SampleUtil;
import com.aftership.sdk.AfterShip;
import com.aftership.sdk.auth.AuthenticationType;

import static com.aftership.sample.tracking.CreateTrackingSample.createTracking;
import static com.aftership.sample.tracking.GetTrackingsSample.getTrackings;

public class SignatureSample {
  public static void main(String[] args) {
    // add sign
    AfterShip afterShipSign = new AfterShip(SampleUtil.getApiKey(), AuthenticationType.AES, SampleUtil.getApiSecret(), SampleUtil.getAftershipOption());
    createTracking(afterShipSign);
    getTrackings(afterShipSign);
  }
}
