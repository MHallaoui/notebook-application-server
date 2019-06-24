package com.oracle.labs.notebookserver.endpoint;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.exception.WrongFormatException;
import com.oracle.labs.notebookserver.model.InterpretationResult;
import com.oracle.labs.notebookserver.model.UserCodePlayLoad;
import com.oracle.labs.notebookserver.parser.ParserFactory;
import com.oracle.labs.notebookserver.parser.ParserResult;
import com.oracle.labs.notebookserver.processor.JavaScriptInterpreter;
import com.oracle.labs.notebookserver.validation.AbstractUserCodeValidator;
import com.oracle.labs.notebookserver.validation.UserCodeValidator;

/**
 * Created by Marouane EL HALLAOUI on 6/22/2019.
 */

@RestController
public class NoteBookEndpoint {

    private final String regex = "%([a-zA-Z]+)\\s([a-zA-Z0-9\\s();=.\"_{}%'\\-\\+]+)";
    private Logger logger = LoggerFactory.getLogger(NoteBookEndpoint.class);
    private Pattern CodePattern = Pattern.compile(regex);

    private AbstractUserCodeValidator requestValidator = new UserCodeValidator();
    private JavaScriptInterpreter processor = new JavaScriptInterpreter();

    @Autowired
    private Environment environement;

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public ResponseEntity evaluate(@RequestBody UserCodePlayLoad request) {
        try {

            requestValidator.validateRequestContent(request, CodePattern);
            ParserResult parsedRequest = ParserFactory.getParser().extractData(request.getCode(), CodePattern);

            InterpretationResult interpretationResult =
                processor.process(parsedRequest.getRawCode(), parsedRequest.getInterpreterType());

            logger.info("code : {} of language type  {} is done sucessfully", parsedRequest.getRawCode(), parsedRequest.getInterpreterType());

            return new ResponseEntity(interpretationResult, HttpStatus.OK);
        } catch (WrongFormatException exception) {
            logger.error("Request : {} had bad Format", request.getCode());
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UnkownInterpreterType exception) {
            logger.error("Interpreter type is undefined", exception.getMessage());
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception generalException) {
            logger.error("Received request {} is invalide", request.getCode());
            return new ResponseEntity(generalException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
