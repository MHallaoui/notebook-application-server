package com.oracle.labs.notebookserver.parser;

import java.util.regex.Pattern;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public abstract class AbstractPlayLoadParser {

    public abstract ParserResult extractData(String receiveCode, Pattern codePattern) throws UnkownInterpreterType;
}
