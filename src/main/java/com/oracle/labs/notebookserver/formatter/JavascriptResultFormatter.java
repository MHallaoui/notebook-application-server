package com.oracle.labs.notebookserver.formatter;

import org.springframework.util.StringUtils;

public class JavascriptResultFormatter implements AbstractInterpreterResultFormatter {

    public static final String ENDL_CARACTER = "\r\n";
    public static final String EMPTY_STRING = "";

    @Override
    public String format(String input) {
        String cleanRawStringResult = StringUtils.replace(input, ENDL_CARACTER, EMPTY_STRING);
        return cleanRawStringResult;
    }
}
