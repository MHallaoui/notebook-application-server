package com.oracle.labs.notebookserver.endpoint.handler;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import com.oracle.labs.notebookserver.endpoint.v1.EndpointRequestHandler;
import com.oracle.labs.notebookserver.model.InputPlayload;
import com.oracle.labs.notebookserver.validation.AbstractPlayloadValidator;
import com.oracle.labs.notebookserver.validation.PlayloadValidatorImplementation;

public abstract class AbstractEndpointRequestHandler {

    public static final String regex = "%([a-zA-Z]+)\\s([a-zA-Z0-9\\s();=.\"_{}%'\\-\\+]+)";
    protected static Pattern CodePattern = Pattern.compile(regex);
    protected Logger logger = LoggerFactory.getLogger(EndpointRequestHandler.class);
    protected AbstractPlayloadValidator requestValidator = new PlayloadValidatorImplementation();

    abstract public ResponseEntity processRequest(InputPlayload request);
}
