package com.oracle.labs.notebookserver.endpoint.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.exception.WrongFormatException;
import com.oracle.labs.notebookserver.model.AbstractInterpretationResult;
import com.oracle.labs.notebookserver.model.InputPlayLoad;
import com.oracle.labs.notebookserver.parser.ParserFactory;
import com.oracle.labs.notebookserver.parser.ParserResult;
import com.oracle.labs.notebookserver.processor.AbstractInterpreterProcessor;
import com.oracle.labs.notebookserver.processor.resolver.InterpreterResolver;

@Component
public class EndpointRequestHandler extends AbstractEndpointRequestHandler {

    @Override
    public ResponseEntity processRequest(InputPlayLoad request) {

        try {

            requestValidator.validateRequestContent(request, CodePattern);

            ParserResult parsedRequest = ParserFactory.getParser().extractData(request.getCode(), CodePattern);

            AbstractInterpreterProcessor targetInterpreter = InterpreterResolver.resolveBy(parsedRequest.getInterpreterType());

            AbstractInterpretationResult interpretationResult = targetInterpreter.processScript(parsedRequest.getRawCode());

            logger.info("code : {} of language type  {} is done successfully", parsedRequest.getRawCode(), parsedRequest.getInterpreterType());

            return new ResponseEntity<>(interpretationResult, HttpStatus.OK);
        } catch (WrongFormatException exception) {
            logger.error("Request : {} had bad Format", request.getCode());
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UnkownInterpreterType exception) {
            logger.error("Interpreter type is undefined", exception.getMessage());
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception generalException) {
            logger.error("Received request {} is invalid", request.getCode());
            return new ResponseEntity(generalException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
