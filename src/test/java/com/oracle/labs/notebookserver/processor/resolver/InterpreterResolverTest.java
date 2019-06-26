package com.oracle.labs.notebookserver.processor.resolver;

import org.junit.Assert;
import org.junit.Test;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.processor.AbstractInterpreterProcessor;
import com.oracle.labs.notebookserver.processor.JavascriptInterpreterProxy;

public class InterpreterResolverTest {

    public static final String JAVASCRIPT = "javascript";

    @Test
    public void shouldReturnJavascriptInterpreterProcessorInstance() throws UnkownInterpreterType {
        AbstractInterpreterProcessor javascriptInterpreterProcessorInstance = InterpreterResolver.resolveBy(JAVASCRIPT);
        Assert.assertNotNull(javascriptInterpreterProcessorInstance);
        Assert.assertTrue(javascriptInterpreterProcessorInstance instanceof JavascriptInterpreterProxy);
    }

    @Test(expected = UnkownInterpreterType.class)
    public void shouldTriggerUnknownInterpreterType() throws UnkownInterpreterType {
        InterpreterResolver.resolveBy("undefinedInterpreter");
    }

    @Test
    public void interpreterResolverShouldFollowSingletonPattern() throws UnkownInterpreterType {
        AbstractInterpreterProcessor javascriptInterpreterProcessorFirstInstance = InterpreterResolver.resolveBy(JAVASCRIPT);
        AbstractInterpreterProcessor javascriptInterpreterProcessorSecondeInstance = InterpreterResolver.resolveBy(JAVASCRIPT);
        Assert.assertNotNull(javascriptInterpreterProcessorFirstInstance);
        Assert.assertNotNull(javascriptInterpreterProcessorSecondeInstance);
        Assert.assertTrue(javascriptInterpreterProcessorFirstInstance == javascriptInterpreterProcessorSecondeInstance);
    }
}
