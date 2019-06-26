package com.oracle.labs.notebookserver.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;

public class PlayloadParserImplementationTest {

    private PlayloadParserImplementation playloadParserImplementation = new PlayloadParserImplementation();
    private String regex = "%([a-zA-Z]+)\\s([a-zA-Z0-9\\s();=.\"_{}%'\\-\\+]+)";
    private Pattern codePattern = Pattern.compile(regex);

    @Test
    public void shouldExtractDataNeededForInterpretationReceived() throws UnkownInterpreterType {
        String jsonCodeFieldValue = "%javascript var a =; 10;";
        Matcher matcher = codePattern.matcher(jsonCodeFieldValue);
        matcher.find();
        ParserResult parsingOperationResult = playloadParserImplementation.extractData(jsonCodeFieldValue, codePattern);

        Assert.assertNotNull(parsingOperationResult);

        Assert.assertEquals("javascript", parsingOperationResult.getInterpreterType());
        Assert.assertEquals("var a =; 10;", parsingOperationResult.getRawCode());
    }
}
