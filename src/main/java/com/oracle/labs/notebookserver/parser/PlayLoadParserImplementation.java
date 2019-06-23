package com.oracle.labs.notebookserver.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class PlayLoadParserImplementation extends AbstractUserCodeParser {


    @Override
    public ParserResult extractData(String receiveCode, Pattern codePattern) throws UnkownInterpreterType {
        Matcher matcher = codePattern.matcher(receiveCode);
        matcher.find();
        if (!matcher.group(1).equals("javascript")) throw new UnkownInterpreterType("interpreter undefined");

        return new ParserResult(matcher.group(2), matcher.group(1));
    }
}
