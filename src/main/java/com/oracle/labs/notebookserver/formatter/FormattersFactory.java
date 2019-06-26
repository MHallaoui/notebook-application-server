package com.oracle.labs.notebookserver.formatter;

public class FormattersFactory {

    static private JavascriptInterpreterResultFormatter javaScriptFormatter;

    static {
        javaScriptFormatter = new JavascriptInterpreterResultFormatter();
    }

    private static JavascriptInterpreterResultFormatter getJavaScriptFormatter() {
        return javaScriptFormatter;
    }

    public static AbstractInterpreterResultFormatter getFormatterFor(String languageName) {
        switch (languageName) {
            case "javascript":
                return getJavaScriptFormatter();
            default:
                throw new RuntimeException("interpreter type is undefined");
        }
    }
}
