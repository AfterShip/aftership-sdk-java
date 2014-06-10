package test;

import code.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CheckpointTest {

    @Test
    public void testPrueba() throws Exception {


        // MyClass is tested
        Checkpoint tester = new Checkpoint();

        // check if multiply(10,5) returns 50
        assertEquals("10 x 5 must be 50", 50, tester.prueba());
    }


}