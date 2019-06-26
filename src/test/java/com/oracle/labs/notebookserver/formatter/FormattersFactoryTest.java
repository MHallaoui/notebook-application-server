package com.oracle.labs.notebookserver.formatter;

import org.junit.Assert;
import org.junit.Test;

public class FormattersFactoryTest {

    @Test
    public void parserFactoryShouldReturnFormatterInstanceForJavascript() throws RuntimeException {
        AbstractInterpreterResultFormatter formatterInstance = FormattersFactory.getFormatterFor("javascript");
        Assert.assertNotNull(formatterInstance);
        Assert.assertTrue(formatterInstance instanceof JavascriptInterpreterResultFormatter);
    }

    @Test(expected = RuntimeException.class)
    public void shouldTriggerUnkownInterpreterTypeWhenLanguageIsUndefined() throws RuntimeException {
        FormattersFactory.getFormatterFor("someUnknowLanguage");
    }
}
