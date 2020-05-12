package com.aftership.sdk.impl;

import java.util.HashMap;
import java.util.Map;
import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.TrackingEndpoint;
import com.aftership.sdk.lib.UrlUtil;
import com.aftership.sdk.model.tracking.*;
import com.aftership.sdk.rest.*;

public class TrackingImpl extends AfterShipEndpoint implements TrackingEndpoint {

    public TrackingImpl(ApiRequest request) {
        super(request);
    }

    @Override
    public DataEntity<SingleTracking> getTracking(SingleTrackingParam param, GetTrackingParams optionalParams) {
        Map<String, String> query = null;
        if (param.getOptionalParams() != null) {
            query = param.getOptionalParams().toMap();
        }
        if (optionalParams != null) {
            if (query == null) {
                query = new HashMap<>();
            }
            query.putAll(optionalParams.toMap());
        }

        String path = UrlUtil.buildTrackingPath(param.getId(), param.getSlug(), param.getTrackingNumber(),
                query, EndpointPath.GET_TRACKING, null);

        return this.request.makeRequest(new RequestConfig(HttpMethod.GET, path),
                null, SingleTracking.class);
    }

    @Override
    public DataEntity<MultiTrackingsData> getTrackings(MultiTrackingsParams optionalParams) {
        Map<String, String> query = optionalParams != null ? optionalParams.toMap() : new HashMap<>();
        String path = UrlUtil.fillPathWithQuery(EndpointPath.GET_TRACKINGS, query);

        return this.request.makeRequest(new RequestConfig(HttpMethod.GET, path),
                null, MultiTrackingsData.class);
    }
}
