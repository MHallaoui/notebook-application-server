package com.oracle.labs.notebookserver.processor.factory;

import org.junit.Assert;
import org.junit.Test;

import com.oracle.labs.notebookserver.processor.JavascriptInterpreterProxy;

public class JavascriptInterpreterProxyFactoryTest {

    @Test
    public void javascriptInterpreterProxyFactoryShouldReturnExpectedInstance() {
        JavascriptInterpreterProxy parserInstance = JavascriptInterpreterProxyFactory.getInterpreter();
        Assert.assertNotNull(parserInstance);
        Assert.assertTrue(parserInstance instanceof JavascriptInterpreterProxy);
    }

    @Test
    public void javascriptInterpreterProxyFactoryShouldRespectSingeletonPattern() {
        JavascriptInterpreterProxy firstParserInstance = JavascriptInterpreterProxyFactory.getInterpreter();
        JavascriptInterpreterProxy secondParserInstance = JavascriptInterpreterProxyFactory.getInterpreter();

        Assert.assertNotNull(firstParserInstance);
        Assert.assertNotNull(secondParserInstance);
        Assert.assertTrue(firstParserInstance instanceof JavascriptInterpreterProxy);
        Assert.assertTrue(secondParserInstance instanceof JavascriptInterpreterProxy);
        Assert.assertTrue(secondParserInstance == firstParserInstance);
    }
}
