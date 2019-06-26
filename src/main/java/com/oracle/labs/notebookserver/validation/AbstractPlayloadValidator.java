package com.oracle.labs.notebookserver.validation;

import java.util.regex.Pattern;

import com.oracle.labs.notebookserver.exception.CodeFormatException;
import com.oracle.labs.notebookserver.exception.UnexpectedPlayloadFormatException;
import com.oracle.labs.notebookserver.model.InputPlayload;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public abstract class AbstractPlayloadValidator {

    abstract protected void matchRequestPattern(String input, Pattern pattern) throws CodeFormatException;

    abstract protected void checkRequestContent(InputPlayload request) throws UnexpectedPlayloadFormatException;

    public void validateRequestContent(InputPlayload request, Pattern pattern) throws UnexpectedPlayloadFormatException, CodeFormatException, RuntimeException {
        checkRequestContent(request);
        matchRequestPattern(request.getCode(), pattern);
    }
}
