package com.oracle.labs.notebookserver.formatter;

public class FormattersFactory {

    static private JavascriptResultFormatter javaScriptFormatter;

    static {
        javaScriptFormatter = new JavascriptResultFormatter();
    }

    private static JavascriptResultFormatter getJavaScriptFormatter() {
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
