package com.oracle.labs.notebookserver.endpoint.handler;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.oracle.labs.notebookserver.model.InputPlayLoad;
import com.oracle.labs.notebookserver.validation.AbstractUserCodeValidator;
import com.oracle.labs.notebookserver.validation.UserCodeValidator;

public abstract class AbstractEndpointRequestHandler {

    protected static final String regex = "%([a-zA-Z]+)\\s([a-zA-Z0-9\\s();=.\"_{}%'\\-\\+]+)";
    protected static Pattern CodePattern = Pattern.compile(regex);
    protected Logger logger = LoggerFactory.getLogger(EndpointRequestHandler.class);
    protected AbstractUserCodeValidator requestValidator = new UserCodeValidator();

    abstract public ResponseEntity processRequest(InputPlayLoad request);
}
