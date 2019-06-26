package com.oracle.labs.notebookserver.formatter;

import org.junit.Assert;
import org.junit.Test;

import static com.oracle.labs.notebookserver.formatter.JavascriptInterpreterResultFormatter.ENDL_CARACTER;

public class JavaScriptInterpreterResultFormatterTest {

    public static final String formatterInput = "{\"result\":\"12\" ";
    private JavascriptInterpreterResultFormatter jsResultFormatter = new JavascriptInterpreterResultFormatter();

    @Test
    public void shouldEliminateEndlCaracterFromExecutionResultWhenItExists() {

        String rawStringResult = new StringBuilder().append(formatterInput)
            .append(ENDL_CARACTER)
            .append("}").toString();
        String expectedResult = new StringBuilder().append(formatterInput)
            .append("}").toString();
        String formattigResult = jsResultFormatter.format(rawStringResult);

        Assert.assertEquals(expectedResult, formattigResult);
    }

    @Test
    public void shouldNotImpactExecutionResultWhenEndlCaracterDidNotExists() {

        String rawStringResult = new StringBuilder().append(formatterInput)
            .append("}").toString();

        String formattigResult = jsResultFormatter.format(rawStringResult);

        Assert.assertEquals(rawStringResult, formattigResult);
    }
}
