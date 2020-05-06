package com.aftership.sdk;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class TestMock {

    @Test
    public void testMockObject(){
        List mockedList = Mockito.mock(List.class);
        Assert.assertTrue(mockedList instanceof List);

        ArrayList mockedArrayList = Mockito.mock(ArrayList.class);
        Assert.assertTrue(mockedArrayList instanceof List);
        Assert.assertTrue(mockedArrayList instanceof ArrayList);
    }

}
