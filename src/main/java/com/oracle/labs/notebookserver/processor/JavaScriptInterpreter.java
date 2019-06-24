package com.oracle.labs.notebookserver.processor;

import java.io.StringWriter;

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

    private StringWriter specificWriter = new StringWriter();

    public InterpretationResult process(String jsCode, String interpreterType) throws UnkownInterpreterType {

        if (interpreterType.equals(TARGET_LANGUAGE)) {
            String jsEvaluationResult = runScript(jsCode);
            return new InterpretationResult(jsEvaluationResult.trim());
        } else {
            throw new UnkownInterpreterType("interpreter undefined");
        }
    }

    private String runScript(String script) {

        clearNashornWriter();
        try {
            nashornEngine.getContext().setWriter(specificWriter);
            nashornEngine.eval(script);
            return specificWriter.toString().replace("\r\n", "");
        } catch (ScriptException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void clearNashornWriter() {
        specificWriter.getBuffer().setLength(0);
    }
}
