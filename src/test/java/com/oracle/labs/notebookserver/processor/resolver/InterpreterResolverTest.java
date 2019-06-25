package com.oracle.labs.notebookserver.processor.resolver;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InterpreterResolverTest {

    @Test
    public void sum_with3numbers() {
        System.out.println("Test1");
        assertEquals(6, Integer.sum( 3, 3 ));
        //Mock
    }
}
