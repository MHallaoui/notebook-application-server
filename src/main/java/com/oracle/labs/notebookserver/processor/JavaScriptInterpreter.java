package com.oracle.labs.notebookserver.processor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.model.InterpretationResult;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class JavaScriptInterpreter {
    private static final String NATIVE_JAVASCRIPT_ENGINE = "nashorn";
    private static final String TARGET_LANGUAGE = "javascript";

    private static ScriptEngineManager scriptEngineManager;
    private static ScriptEngine nashornEngine;

    static {
        scriptEngineManager = new ScriptEngineManager();
        nashornEngine = scriptEngineManager.getEngineByName(NATIVE_JAVASCRIPT_ENGINE);
    }

    public InterpretationResult process(String jsCode, String interpreterType) throws UnkownInterpreterType {

        if (interpreterType.equals(TARGET_LANGUAGE)) {
            Object jsEvaluationResult = runScript(jsCode);
            return new InterpretationResult(jsEvaluationResult.toString());
        } else {
            throw new UnkownInterpreterType("interpreter undefined");
        }
    }

    private Object runScript(String script) {
        try {
            return nashornEngine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
            return null;
        }
    }
}
