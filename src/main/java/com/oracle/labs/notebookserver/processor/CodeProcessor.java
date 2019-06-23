package com.oracle.labs.notebookserver.processor;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.model.InterpretationResult;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class CodeProcessor {
    private static final String TARGET_LANGUAGE_ALIAS = "js";
    private static final String TARGET_LANGUAGE = "javascript";
    private Context jsContext = Context.create(TARGET_LANGUAGE_ALIAS);

    public InterpretationResult process(String code, String interpreterType) throws UnkownInterpreterType {

        if (interpreterType.equals(TARGET_LANGUAGE)) {
            Value jsEvaluationResult = runScript(code, jsContext);
            return new InterpretationResult(jsEvaluationResult.toString());
        } else {
            throw new UnkownInterpreterType("interpreter undefined");
        }

    }

    private Value runScript(String script, Context context) {
        return context.eval(TARGET_LANGUAGE_ALIAS, script);
    }
}
