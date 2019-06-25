package com.oracle.labs.notebookserver.processor.factory;

import com.oracle.labs.notebookserver.processor.JavaScriptInterpreterProxy;

public class JavaScriptInterpreterProxyFactory {

    static private JavaScriptInterpreterProxy javaScriptInterpreterProxy;

    static {
        javaScriptInterpreterProxy = new JavaScriptInterpreterProxy();
    }

    public static JavaScriptInterpreterProxy getInterpreter() {
        return javaScriptInterpreterProxy;
    }
}
