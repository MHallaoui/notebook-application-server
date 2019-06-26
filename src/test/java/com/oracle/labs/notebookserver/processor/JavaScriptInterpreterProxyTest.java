package com.oracle.labs.notebookserver.processor;

import org.junit.Assert;
import org.junit.Test;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.model.InterpretationResult;

public class JavascriptInterpreterProxyTest {

    private JavascriptInterpreterProxy javascriptInterpreter = new JavascriptInterpreterProxy();

    @Test
    public void shouldEvaluateSuccessfullySimpleValidCodeWithOutput() throws UnkownInterpreterType, NoSuchFieldException, IllegalAccessException {
        InterpretationResult resultWrapper = javascriptInterpreter.processScript("var a = 10; print(a);");
        Assert.assertEquals(resultWrapper.getResult(), "10");
    }

    @Test
    public void shouldEvaluateSuccessfullyValidCodeWithOutput() throws UnkownInterpreterType, NoSuchFieldException, IllegalAccessException {
        InterpretationResult resultWrapper = javascriptInterpreter.processScript("var a = 10; var b = a + 1;function someFunction(b) { return b + 1; }; print( someFunction(b));");
        Assert.assertEquals(resultWrapper.getResult(), "12");
    }

    @Test(expected = RuntimeException.class)
    public void shouldTriggerRuntimeExceptionWhenCodeIsInvalid() {
        InterpretationResult resultWrapper = javascriptInterpreter.processScript("var a = 10; function someInvalidFunction(parameter) }");
    }
}
