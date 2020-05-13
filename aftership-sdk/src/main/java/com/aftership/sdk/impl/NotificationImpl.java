package com.aftership.sdk.impl;

import java.util.Map;
import com.aftership.sdk.endpoint.AfterShipEndpoint;
import com.aftership.sdk.endpoint.NotificationEndpoint;
import com.aftership.sdk.lib.UrlUtil;
import com.aftership.sdk.model.notification.NotificationWrapper;
import com.aftership.sdk.model.tracking.SingleTrackingParam;
import com.aftership.sdk.rest.ApiRequest;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.rest.HttpMethod;
import com.aftership.sdk.rest.RequestConfig;

/**
 * NotificationEndpoint's implementation class
 */
public class NotificationImpl extends AfterShipEndpoint implements NotificationEndpoint {

    public NotificationImpl(ApiRequest request) {
        super(request);
    }

    @Override
    public DataEntity<NotificationWrapper> getNotification(SingleTrackingParam param) {
        Map.Entry<Boolean, DataEntity<NotificationWrapper>> error = errorOfSingleTrackingParam(param);
        if (error.getKey()) {
            return error.getValue();
        }

        String path = UrlUtil.buildTrackingPath(param.getId(), param.getSlug(), param.getTrackingNumber(),
                null, EndpointPath.GET_NOTIFICATION, null);

        return this.request.makeRequest(new RequestConfig(HttpMethod.GET, path),
                null, NotificationWrapper.class);
    }

    @Override
    public DataEntity<NotificationWrapper> addNotification(SingleTrackingParam param,
                                                           NotificationWrapper notificationWrapper) {
        Map.Entry<Boolean, DataEntity<NotificationWrapper>> error = errorOfSingleTrackingParam(param);
        if (error.getKey()) {
            return error.getValue();
        }

        String path = UrlUtil.buildTrackingPath(param.getId(), param.getSlug(), param.getTrackingNumber(),
                null, EndpointPath.ADD_NOTIFICATION, EndpointPath.ADD_NOTIFICATION_ACTION);

        return this.request.makeRequest(new RequestConfig(HttpMethod.POST, path),
                notificationWrapper, NotificationWrapper.class);
    }

    @Override
    public DataEntity<NotificationWrapper> removeNotification(SingleTrackingParam param) {
        Map.Entry<Boolean, DataEntity<NotificationWrapper>> error = errorOfSingleTrackingParam(param);
        if (error.getKey()) {
            return error.getValue();
        }

        String path = UrlUtil.buildTrackingPath(param.getId(), param.getSlug(), param.getTrackingNumber(),
                null, EndpointPath.REMOVE_NOTIFICATION, EndpointPath.REMOVE_NOTIFICATION_ACTION);

        //new Object(), fix for 'method POST must have a request body'
        return this.request.makeRequest(new RequestConfig(HttpMethod.POST, path),
                new Object(), NotificationWrapper.class);
    }

}
