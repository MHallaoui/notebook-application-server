package com.oracle.labs.notebookserver.validation;

import java.util.regex.Pattern;

import com.oracle.labs.notebookserver.exception.CodeFormatException;
import com.oracle.labs.notebookserver.exception.UnexpectedPlayloadFormatException;
import com.oracle.labs.notebookserver.model.InputPlayload;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class PlayloadValidatorImplementation extends AbstractPlayloadValidator {

    @Override
    protected void matchRequestPattern(String receivedCode, Pattern pattern) throws CodeFormatException {
        if (receivedCodeUnmatchPattern(receivedCode, pattern)) {
            throw new CodeFormatException("received code did not respect Javascript code pattern");
        }
    }

    @Override
    protected void checkRequestContent(InputPlayload request) throws UnexpectedPlayloadFormatException {

        if (request == null) {
            new RuntimeException("request is null or invalid Json input Format");
        }

        String receivedCode = request.getCode();
        if (receivedCode.isEmpty()) {
            throw new UnexpectedPlayloadFormatException("request content has a Json input having code field empty");
        }
    }

    private boolean receivedCodeUnmatchPattern(String receivedCode, Pattern pattern) {
        return !pattern.matcher(receivedCode).matches();
    }
}
