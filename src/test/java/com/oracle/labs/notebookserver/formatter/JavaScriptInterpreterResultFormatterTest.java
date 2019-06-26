package com.oracle.labs.notebookserver.formatter;

import org.junit.Assert;
import org.junit.Test;

import static com.oracle.labs.notebookserver.formatter.JavascriptInterpreterResultFormatter.ENDL_CARACTER;

public class JavaScriptInterpreterResultFormatterTest {

    private JavascriptInterpreterResultFormatter jsResultFormatter = new JavascriptInterpreterResultFormatter();

    @Test
    public void shouldEliminateEndlCaracterFromExecutionResultWhenItExists() {

        String rawStringResult = new StringBuilder().append("{\"result\":\"12\" ")
            .append(ENDL_CARACTER)
            .append("}").toString();
        String expectedResult = new StringBuilder().append("{\"result\":\"12\" ")
            .append("}").toString();
        String formattigResult = jsResultFormatter.format(rawStringResult);

        Assert.assertEquals(expectedResult, formattigResult);
    }
}
