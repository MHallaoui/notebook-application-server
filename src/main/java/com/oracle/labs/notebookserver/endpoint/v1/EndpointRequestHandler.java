package com.oracle.labs.notebookserver.endpoint.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.oracle.labs.notebookserver.endpoint.handler.AbstractEndpointRequestHandler;
import com.oracle.labs.notebookserver.exception.CodeFormatException;
import com.oracle.labs.notebookserver.exception.UnexpectedPlayloadFormatException;
import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.model.AbstractInterpretationResult;
import com.oracle.labs.notebookserver.model.InputPlayload;
import com.oracle.labs.notebookserver.parser.ParserFactory;
import com.oracle.labs.notebookserver.parser.ParserResult;
import com.oracle.labs.notebookserver.processor.AbstractInterpreterProcessor;
import com.oracle.labs.notebookserver.processor.resolver.InterpreterResolver;

@Component
public class EndpointRequestHandler extends AbstractEndpointRequestHandler {

    @Override
    public ResponseEntity processRequest(InputPlayload request) {

        try {

            requestValidator.validateRequestContent(request, CodePattern);

            ParserResult parsedRequest = ParserFactory.getParser().extractData(request.getCode(), CodePattern);

            AbstractInterpreterProcessor targetInterpreter = InterpreterResolver.resolveBy(parsedRequest.getInterpreterType());

            AbstractInterpretationResult interpretationResult = targetInterpreter.processScript(parsedRequest.getRawCode());

            logger.info("Execution of {} of language type  {} is done successfully", parsedRequest.getRawCode(), parsedRequest.getInterpreterType());

            return new ResponseEntity<>(interpretationResult, HttpStatus.OK);
        } catch (UnexpectedPlayloadFormatException exception) {
            logger.error("Json Request {} had bad Format", request);
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CodeFormatException exception) {
            logger.error("Code {} is not valid", request.getCode());
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (UnkownInterpreterType exception) {
            logger.error("Interpreter type is undefined", exception.getMessage());
            return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_IMPLEMENTED);
        } catch (Exception generalException) {
            logger.error("Received request {} is invalid", request);
            return new ResponseEntity(generalException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
