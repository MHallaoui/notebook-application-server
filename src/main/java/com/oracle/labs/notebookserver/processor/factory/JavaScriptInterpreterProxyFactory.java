package com.oracle.labs.notebookserver.processor.factory;

import com.oracle.labs.notebookserver.processor.JavascriptInterpreterProxy;

public class JavascriptInterpreterProxyFactory {

    static private JavascriptInterpreterProxy javaScriptInterpreterProxy;

    static {
        javaScriptInterpreterProxy = new JavascriptInterpreterProxy();
    }

    public static JavascriptInterpreterProxy getInterpreter() {
        return javaScriptInterpreterProxy;
    }
}
