package com.oracle.labs.notebookserver.validation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.regex.Pattern;

import com.oracle.labs.notebookserver.endpoint.handler.AbstractEndpointRequestHandler;
import com.oracle.labs.notebookserver.exception.CodeFormatException;
import com.oracle.labs.notebookserver.exception.UnexpectedPlayloadFormatException;
import com.oracle.labs.notebookserver.model.InputPlayload;

public class PlayloadValidatorImplementationTest {

    private PlayloadValidatorImplementation validator = new PlayloadValidatorImplementation();
    private Pattern pattern;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = RuntimeException.class)
    public void shouldTriggerRuntimeExceptionWhenRequestIsNull() throws UnexpectedPlayloadFormatException, CodeFormatException {
        validator.validateRequestContent(null, pattern);
    }

    @Test(expected = UnexpectedPlayloadFormatException.class)
    public void shouldNotValidatPlayloadRequestIsEmpty() throws UnexpectedPlayloadFormatException, CodeFormatException {
        InputPlayload playload = Mockito.mock(InputPlayload.class);
        Mockito.when(playload.getCode()).thenReturn("");
        validator.validateRequestContent(playload, pattern);
    }

    @Test(expected = CodeFormatException.class)
    public void shouldNotValidatPlayloadBeginWithUnexpectedCaracter() throws UnexpectedPlayloadFormatException, CodeFormatException {
        InputPlayload playload = Mockito.mock(InputPlayload.class);
        pattern = Pattern.compile(AbstractEndpointRequestHandler.regex);
        Mockito.when(playload.getCode()).thenReturn("+someinterpreter withsomecode");
        validator.validateRequestContent(playload, pattern);
    }

    @Test(expected = CodeFormatException.class)
    public void shouldNotValidatInterpreterScriptContainingInvalidCaracters() throws UnexpectedPlayloadFormatException, CodeFormatException {
        InputPlayload playload = Mockito.mock(InputPlayload.class);
        pattern = Pattern.compile(AbstractEndpointRequestHandler.regex);
        Mockito.when(playload.getCode()).thenReturn("%someinterpreter -*-$-*p-$*p^-*-^-p");
        validator.validateRequestContent(playload, pattern);
    }

    @Test
    public void shouldNotValidatInterpreterScriptWhenIsEmpty() throws UnexpectedPlayloadFormatException, CodeFormatException {
        InputPlayload playload = Mockito.mock(InputPlayload.class);
        pattern = Pattern.compile(AbstractEndpointRequestHandler.regex);
        Mockito.when(playload.getCode()).thenReturn("%someinterpreter  ");
        validator.validateRequestContent(playload, pattern);
    }
}
