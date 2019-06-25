package com.oracle.labs.notebookserver.parser;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class ParserFactory {
    private static AbstractPlayLoadParser parserInstace;

    static {
        parserInstace = new PlayLoadParserImplementation();
    }

    public static AbstractPlayLoadParser getParser() {
        return parserInstace;
    }
}
