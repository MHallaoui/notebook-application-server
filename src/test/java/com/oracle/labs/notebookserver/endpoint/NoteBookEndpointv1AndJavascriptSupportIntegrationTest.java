package com.oracle.labs.notebookserver.endpoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteBookEndpointv1AndJavascriptSupportIntegrationTest {
    public static final String EXECUTE_ENDPOINT_PATH = "/execute";
    public static final String HTTP_LOCALHOST_BASIC_URL = "http://localhost:";
    TestRestTemplate httpRequester = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        configureHeadersInRequest();
    }

    @Test
    public void shouldEvaluateJavascriptCodeInPlayLoadSuccessfullyWithOutput() throws Exception {
        String validJavaScriptPlayLoad = new StringBuilder().append("{")
                .append("\"code\": \"%javascript  var a = 10; var b = a + 1;function someFunction(b) { return b + 1; }; print( someFunction(b));\"")
                .append("}")
                .toString();

        HttpEntity<String> httpRestRequest = createRequestEntityObject(validJavaScriptPlayLoad);
        ResponseEntity<String> response = sendRequest(httpRestRequest);

        String currentExecutionResult = response.getBody();
        String expectedExecutionResult = new StringBuilder().append("{\"result\"").append(":" + "\"12\"").append("}").toString();

        assertEquals(expectedExecutionResult, currentExecutionResult);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldEvaluateJavascriptCodeInPlayLoadSuccessfullyWithoutOutput() throws Exception {
        String validJavaScriptPlayLoad = new StringBuilder().append("{").append("\"code\": \"%javascript  var a = 10; var b = a + 1\"").append("}").toString();

        HttpEntity<String> httpRestRequest = createRequestEntityObject(validJavaScriptPlayLoad);
        ResponseEntity<String> response = sendRequest(httpRestRequest);

        String currentExecutionResult = response.getBody();
        String expectedExecutionResult = new StringBuilder().append("{\"result\"").append(":").append("\"\"").append("}").toString();

        assertEquals(expectedExecutionResult, currentExecutionResult);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldTriggerHttpMessageNotReadableExceptionWhenJSONMappingFailed() {
        String validJavaScriptPlayLoad = new StringBuilder().append("{").append("\"code: \"%javascript  var a = 10; var b = a + 1;\"").append("}").toString();

        HttpEntity<String> httpRestRequest = createRequestEntityObject(validJavaScriptPlayLoad);

        ResponseEntity<String> response = sendRequest(httpRestRequest);

        String expectedException1 = "org.springframework.http.converter.HttpMessageNotReadableException";
        String expectedException2 = "org.springframework.http.converter.HttpMessageNotReadableException";

        assertTrue(response.getBody().contains(expectedException1));
        assertTrue(response.getBody().contains(expectedException2));
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldReturnSpecificMessageWhenInterpreterIsUnsupported() {
        String unsupportedInterpreter = "blabla";
        String validJavaScriptPlayLoad = new StringBuilder().append("{").append("\"code\": \"%").append(unsupportedInterpreter).append("  var a = 10; var b = a + 1\"").append("}").toString();

        HttpEntity<String> httpRestRequest = createRequestEntityObject(validJavaScriptPlayLoad);
        ResponseEntity<String> response = sendRequest(httpRestRequest);

        String currentExecutionResult = response.getBody();
        String expectedExecutionResult = "interpreter is not supported";

        assertEquals(expectedExecutionResult, currentExecutionResult);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_IMPLEMENTED);
    }

    @Test
    public void shouldTriggerExecutionExceptionWhenJavaScriptCodeIsWrong() throws Exception {
        String validJavaScriptPlayLoad = new StringBuilder().append("{").append("\"code\": \"%javascript  var a =; 10\"").append("}").toString();

        HttpEntity<String> httpRestRequest = createRequestEntityObject(validJavaScriptPlayLoad);
        ResponseEntity<String> response = sendRequest(httpRestRequest);

        String expectedExceptionTypeName = "javax.script.ScriptException";

        assertTrue(response.getBody().contains(expectedExceptionTypeName));
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> sendRequest(HttpEntity<String> httpRestRequest) {
        return httpRequester.exchange(
                createEndpointURLWithPort(), HttpMethod.POST, httpRestRequest, String.class);
    }

    private HttpEntity<String> createRequestEntityObject(String valideJavaScriptPlayLoad) {
        return new HttpEntity<String>(valideJavaScriptPlayLoad, headers);
    }

    private String createEndpointURLWithPort() {
        return HTTP_LOCALHOST_BASIC_URL + port + EXECUTE_ENDPOINT_PATH;
    }

    private void configureHeadersInRequest() {
        List<MediaType> acceptMediaType = new ArrayList<>();
        acceptMediaType.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptMediaType);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
}