package com.oracle.labs.notebookserver.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class PlayLoadParserImplementation extends AbstractPlayLoadParser {

    public static final int INTERPRETER_GROUP_INDEX = 1;
    public static final int RAW_CODE_GROUP_INDEX = 2;

    @Override
    public ParserResult extractData(String receiveCode, Pattern codePattern) throws UnkownInterpreterType {
        Matcher matcher = codePattern.matcher(receiveCode);
        matcher.find();

        String targetInterpreterName = matcher.group(INTERPRETER_GROUP_INDEX);
        String rawCode = matcher.group(RAW_CODE_GROUP_INDEX);

        return new ParserResult(rawCode, targetInterpreterName);
    }
}
