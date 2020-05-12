package com.aftership.sdk.endpoint;

import com.aftership.sdk.model.tracking.*;
import com.aftership.sdk.rest.DataEntity;

public interface TrackingEndpoint {

    DataEntity<SingleTracking> getTracking(SingleTrackingParam param, GetTrackingParams optionalParams);

    DataEntity<MultiTrackingsData> getTrackings(MultiTrackingsParams optionalParams);

}
