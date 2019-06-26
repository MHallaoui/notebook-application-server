package com.oracle.labs.notebookserver.formatter;

public class FormattersFactory {

    static private JavascriptInterpreterResultFormatter javaScriptFormatter;

    static {
        javaScriptFormatter = new JavascriptInterpreterResultFormatter();
    }

    public static JavascriptInterpreterResultFormatter getJavaScriptFormatter() {
        return javaScriptFormatter;
    }
}
