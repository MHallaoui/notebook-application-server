package com.oracle.labs.notebookserver.processor;

import java.io.StringWriter;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.oracle.labs.notebookserver.formatter.FormattersFactory;
import com.oracle.labs.notebookserver.model.AbstractInterpretationResult;
import com.oracle.labs.notebookserver.model.InterpretationResult;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */

public class JavascriptInterpreterProxy extends AbstractInterpreterProcessor<AbstractInterpretationResult, StringWriter> {

    private static final String NATIVE_JAVASCRIPT_ENGINE = "nashorn";
    private static final String LANGUAGE_NAME = "javascript";
    private static ScriptEngineManager scriptEngineManager;
    private static ScriptEngine nashornEngine;

    static {
        scriptEngineManager = new ScriptEngineManager();
        nashornEngine = scriptEngineManager.getEngineByName(NATIVE_JAVASCRIPT_ENGINE);
    }

    public JavascriptInterpreterProxy() {
        super(new StringWriter(), FormattersFactory.getFormatterFor(LANGUAGE_NAME));
        redirectOutputToDesiredWrapper();
    }

    private void redirectOutputToDesiredWrapper() {
        nashornEngine.getContext().setWriter(getExecutionResultOutputWrapper());
    }

    @Override
    public InterpretationResult processScript(String jsCode) {
        String result = runScript(jsCode);
        return new InterpretationResult(result);
    }

    private String runScript(String script) {

        clearWrapperBeforeExecution();

        try {
            nashornEngine.eval(script);
            String evaluationResult = formattedExecutionResult();

            logSuccessfullExecution(script, evaluationResult);
            return evaluationResult;
        } catch (ScriptException e) {
            logUnsuccessfullExecution(script, e);
            throw new RuntimeException(e);
        }
    }

    private void logUnsuccessfullExecution(String script, ScriptException e) {
        getLogger().error("Javascript code {} execution failed  caused by : {}", script, e.getMessage());
    }

    private void logSuccessfullExecution(String script, String evaluationResult) {
        getLogger().info("Javascript code {} execution success with output result: {}", script, evaluationResult);
    }

    private void clearWrapperBeforeExecution() {
        super.getExecutionResultOutputWrapper().getBuffer().setLength(0);
    }
}
