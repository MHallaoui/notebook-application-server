package com.oracle.labs.notebookserver.parser;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class ParserFactory {
    private static AbstractUserCodeParser parserInstace;

    static {
        parserInstace = new PlayLoadParserImplementation();
    }

    public static AbstractUserCodeParser getParser() {
        return parserInstace;
    }
}
