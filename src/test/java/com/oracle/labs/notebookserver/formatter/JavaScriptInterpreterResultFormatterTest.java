package com.oracle.labs.notebookserver.formatter;

import org.junit.Assert;
import org.junit.Test;

import static com.oracle.labs.notebookserver.formatter.JavaScriptInterpreterResultFormatter.ENDL_CARACTER;

public class JavaScriptInterpreterResultFormatterTest {

    private JavaScriptInterpreterResultFormatter jsResultFormatter = new JavaScriptInterpreterResultFormatter();


    @Test
    public void shouldEliminateEndlCaracterFromExecutionResult() {

        String rawStringResult = new StringBuilder().append("{\"result\":\"12\" ")
                .append(ENDL_CARACTER)
                .append("}").toString();
        String expectedResult = new StringBuilder().append("{\"result\":\"12\" ")
                .append("}").toString();
        String formattigResult = jsResultFormatter.format(rawStringResult);

        Assert.assertEquals(expectedResult, formattigResult);

    }
}
