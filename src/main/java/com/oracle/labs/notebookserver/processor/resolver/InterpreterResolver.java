package com.oracle.labs.notebookserver.processor.resolver;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.processor.AbstractInterpreterProcessor;
import com.oracle.labs.notebookserver.processor.factory.JavascriptInterpreterProxyFactory;

public class InterpreterResolver {

    public static AbstractInterpreterProcessor resolveBy(String interpreterType) throws UnkownInterpreterType {

        switch (interpreterType) {

            case "javascript":
                return JavascriptInterpreterProxyFactory.getInterpreter();
            default:
                throw new UnkownInterpreterType("interpreter is not supported");
        }
    }
}
