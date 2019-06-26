package com.oracle.labs.notebookserver.parser;

import org.junit.Assert;
import org.junit.Test;

public class ParserFactoryTest {
    @Test
    public void parserFactoryShouldReturnExpectedParserInstance() {
        AbstractPlayloadParser parserInstance = ParserFactory.getParser();
        Assert.assertNotNull(parserInstance);
        Assert.assertTrue(parserInstance instanceof AbstractPlayloadParser);
    }

    @Test
    public void parserFactoryRespectSingeletonPattern() {
        AbstractPlayloadParser firstParserInstance = ParserFactory.getParser();
        AbstractPlayloadParser secondParserInstance = ParserFactory.getParser();
        Assert.assertNotNull(firstParserInstance);
        Assert.assertNotNull(secondParserInstance);
        Assert.assertTrue(firstParserInstance instanceof AbstractPlayloadParser);
        Assert.assertTrue(secondParserInstance instanceof AbstractPlayloadParser);
        Assert.assertTrue(secondParserInstance.equals(firstParserInstance));
    }
}
