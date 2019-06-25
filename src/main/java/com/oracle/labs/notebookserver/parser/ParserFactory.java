package com.oracle.labs.notebookserver.parser;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class ParserFactory {
    private static AbstractPlayloadParser parserInstace;

    static {
        parserInstace = new PlayloadParserImplementation();
    }

    public static AbstractPlayloadParser getParser() {
        return parserInstace;
    }
}
