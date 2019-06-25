package com.oracle.labs.notebookserver.validation;

import java.util.regex.Pattern;

import com.oracle.labs.notebookserver.exception.WrongFormatException;
import com.oracle.labs.notebookserver.model.InputPlayLoad;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class UserCodeValidator extends AbstractUserCodeValidator {

    @Override
    protected void matchRequestPattern(String receivedCode, Pattern pattern) throws WrongFormatException {
        if (receivedCodeUnmatchPattern(receivedCode, pattern)) {
            throw new WrongFormatException("received code did not respect Javascript code pattern");
        }
    }

    @Override
    protected void checkRequestContent(InputPlayLoad request) throws WrongFormatException {

        if (request == null) {
            new IllegalArgumentException("request is null or invalid Json input Format");
        }

        String receivedCode = request.getCode();
        if (receivedCode.isEmpty()) {
            throw new WrongFormatException("request content has a Json input having code field empty");
        }
    }

    private boolean receivedCodeUnmatchPattern(String receivedCode, Pattern pattern) {
        return !pattern.matcher(receivedCode).matches();
    }
}
