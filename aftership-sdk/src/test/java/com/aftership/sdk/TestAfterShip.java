package com.aftership.sdk;

import com.aftership.sdk.error.AftershipError;
import com.aftership.sdk.impl.CourierImpl;
import com.aftership.sdk.model.courier.CourierList;
import com.aftership.sdk.rest.ApiRequest;
import com.aftership.sdk.rest.DataEntity;
import com.aftership.sdk.rest.ResponseEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class TestAfterShip {

    @Test
    public void test() {
        AfterShip afterShipMock = Mockito.mock(AfterShip.class);
        Mockito.when(afterShipMock.getApiKey()).thenReturn(TestUtil.YOUR_API_KEY);

        //TODO: incomplete
        ApiRequest apiRequestMock = Mockito.mock(ApiRequest.class);
        Mockito.when(apiRequestMock.makeRequest(null, null, null))
                .thenReturn(null);

        Mockito.when(afterShipMock.getCourierEndpoint()).thenReturn(new CourierImpl(apiRequestMock));

        Assertions.assertEquals(TestUtil.YOUR_API_KEY, afterShipMock.getApiKey());
        Assertions.assertTrue(afterShipMock.getCourierEndpoint() instanceof CourierImpl);
    }
}
