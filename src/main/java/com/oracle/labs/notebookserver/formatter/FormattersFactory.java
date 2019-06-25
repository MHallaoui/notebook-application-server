package com.oracle.labs.notebookserver.formatter;

public class FormattersFactory {

    static private JavaScriptInterpreterResultFormatter javaScriptFormatter;

    static {
        javaScriptFormatter = new JavaScriptInterpreterResultFormatter();
    }

    public static JavaScriptInterpreterResultFormatter getJavaScriptFormatter() {
        return javaScriptFormatter;
    }
}
