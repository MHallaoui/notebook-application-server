package com.oracle.labs.notebookserver.processor;

import java.io.StringWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.formatter.FormattersFactory;
import com.oracle.labs.notebookserver.model.AbstractInterpretationResult;
import com.oracle.labs.notebookserver.model.InterpretationResult;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */

public class JavaScriptInterpreterProxy extends AbstractInterpreterProcessor<AbstractInterpretationResult, StringWriter> {

    private static final String NATIVE_JAVASCRIPT_ENGINE = "nashorn";
    private static ScriptEngineManager scriptEngineManager;
    private static ScriptEngine nashornEngine;

    static {
        scriptEngineManager = new ScriptEngineManager();
        nashornEngine = scriptEngineManager.getEngineByName(NATIVE_JAVASCRIPT_ENGINE);
    }

    public JavaScriptInterpreterProxy() {
        super(new StringWriter(), FormattersFactory.getJavaScriptFormatter());
        redirectOutputToDesiredWrapper();
    }

    private void redirectOutputToDesiredWrapper() {
        nashornEngine.getContext().setWriter(getExecutionResultOutputWrapper());
    }

    @Override
    public InterpretationResult processScript(String jsCode) throws UnkownInterpreterType {
        runScript(jsCode);
        String result = formattedExecutionResult();
        return new InterpretationResult(result);
    }

    private String runScript(String script) {

        clearWrapperBeforeExecution();

        try {
            nashornEngine.eval(script);
            String result = formattedExecutionResult();
            getLogger().info("Javascript code execution is done Successfully");
            getLogger().info("Javascript code execution result output -- {}", result);
            return result;
        } catch (ScriptException e) {
            getLogger().error("Javascript code execution failed", e.getMessage());
            getLogger().warn(" Javascript code that generate the error is {} ", script);
            return null;
        }
    }

    private void clearWrapperBeforeExecution() {
        super.getExecutionResultOutputWrapper().getBuffer().setLength(0);
    }
}
