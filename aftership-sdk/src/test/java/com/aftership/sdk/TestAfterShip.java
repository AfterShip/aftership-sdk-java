package com.aftership.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.aftership.sdk.impl.CourierImpl;
import com.aftership.sdk.rest.ApiRequest;

public class TestAfterShip {

    @Test
    public void test() {
        AfterShip afterShipMock = Mockito.mock(AfterShip.class);
        Mockito.when(afterShipMock.getApiKey()).thenReturn(TestUtil.YOUR_API_KEY);

        //TODO(incomplete)
        ApiRequest apiRequestMock = Mockito.mock(ApiRequest.class);
        Mockito.when(apiRequestMock.makeRequest(null, null, null))
                .thenReturn(null);

        Mockito.when(afterShipMock.getCourierEndpoint()).thenReturn(new CourierImpl(apiRequestMock));

        Assertions.assertEquals(TestUtil.YOUR_API_KEY, afterShipMock.getApiKey());
        Assertions.assertTrue(afterShipMock.getCourierEndpoint() instanceof CourierImpl);
    }
}
