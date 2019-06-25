package com.oracle.labs.notebookserver.validation;

import java.util.regex.Pattern;

import com.oracle.labs.notebookserver.exception.WrongFormatException;
import com.oracle.labs.notebookserver.model.InputPlayLoad;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public abstract class AbstractUserCodeValidator {

    abstract protected void matchRequestPattern(String input, Pattern pattern) throws WrongFormatException;

    abstract protected void checkRequestContent(InputPlayLoad request) throws WrongFormatException;

    public void validateRequestContent(InputPlayLoad request, Pattern pattern) throws WrongFormatException {
        checkRequestContent(request);
        matchRequestPattern(request.getCode(), pattern);
    }
}
