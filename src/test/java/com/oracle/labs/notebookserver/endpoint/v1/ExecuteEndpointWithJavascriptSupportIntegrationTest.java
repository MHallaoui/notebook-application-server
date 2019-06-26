package com.oracle.labs.notebookserver.endpoint.v1;

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
public class ExecuteEndpointWithJavascriptSupportIntegrationTest {
    public static final String EXECUTE_ENDPOINT_PATH = "/execute";
    public static final String HTTP_LOCALHOST_BASIC_URL = "http://localhost:";
    public static final String LEFT_BRACE = "{";
    public static final String RIGHT_BRACE = "}";
    public static final String EMPTY_JSON_PROPERTY_VALUE = "\"\"";
    TestRestTemplate httpRequester = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        configureHeadersInRequest();
    }

    @Test
    public void shouldEvaluateJavascriptCodeSuccessfullyWithOutput() throws Exception {
        String validJavaScriptPlayLoad = new StringBuilder().append(LEFT_BRACE)
                .append("\"code\": \"%javascript  var a = 10; var b = a + 1;function someFunction(b) { return b + 1; }; print( someFunction(b));\"")
                .append(RIGHT_BRACE)
                .toString();

        HttpEntity<String> httpRestRequest = createRequestEntityObject(validJavaScriptPlayLoad);
        ResponseEntity<String> response = sendRequest(httpRestRequest);

        String currentExecutionResult = response.getBody();
        String expectedExecutionResult = new StringBuilder().append("{\"result\"").append(":" + "\"12\"").append(RIGHT_BRACE).toString();

        assertEquals(expectedExecutionResult, currentExecutionResult);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldEvaluateJavascriptCodeSuccessfullyWithoutOutput() throws Exception {
        String validJavaScriptPlayLoad = new StringBuilder().append(LEFT_BRACE).append("\"code\": \"%javascript  var a = 10; var b = a + 1\"").append(RIGHT_BRACE).toString();

        HttpEntity<String> httpRestRequest = createRequestEntityObject(validJavaScriptPlayLoad);
        ResponseEntity<String> response = sendRequest(httpRestRequest);

        String currentExecutionResult = response.getBody();
        String expectedExecutionResult = new StringBuilder().append("{\"result\"").append(":").append(EMPTY_JSON_PROPERTY_VALUE).append(RIGHT_BRACE).toString();

        assertEquals(expectedExecutionResult, currentExecutionResult);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldTriggerExceptionWhenJSsonMappingFailed() {
        String validJavaScriptPlayLoad = new StringBuilder().append(LEFT_BRACE).append("\"code: \"%javascript  var a = 10; var b = a + 1;\"").append(RIGHT_BRACE).toString();

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
        String validJavaScriptPlayLoad = new StringBuilder().append(LEFT_BRACE).append("\"code\": \"%").append(unsupportedInterpreter).append("  var a = 10; var b = a + 1\"").append(RIGHT_BRACE).toString();

        HttpEntity<String> httpRestRequest = createRequestEntityObject(validJavaScriptPlayLoad);
        ResponseEntity<String> response = sendRequest(httpRestRequest);

        String currentExecutionResult = response.getBody();
        String expectedExecutionResult = "interpreter is not supported";

        assertEquals(expectedExecutionResult, currentExecutionResult);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_IMPLEMENTED);
    }

    @Test
    public void shouldTriggerExecutionExceptionWhenJavascriptCodeIsWrong() throws Exception {
        String validJavaScriptPlayLoad = new StringBuilder().append(LEFT_BRACE).append("\"code\": \"%javascript  var a =; 10\"").append(RIGHT_BRACE).toString();

        HttpEntity<String> httpRestRequest = createRequestEntityObject(validJavaScriptPlayLoad);
        ResponseEntity<String> response = sendRequest(httpRestRequest);

        String expectedExceptionTypeName = "javax.script.ScriptException";

        assertTrue(response.getBody().contains(expectedExceptionTypeName));
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> sendRequest(HttpEntity<String> httpRestRequest) {
        return httpRequester.exchange(
                createEndpointUrlWithPort(), HttpMethod.POST, httpRestRequest, String.class);
    }

    private HttpEntity<String> createRequestEntityObject(String valideJavaScriptPlayLoad) {
        return new HttpEntity<String>(valideJavaScriptPlayLoad, headers);
    }

    private String createEndpointUrlWithPort() {
        return HTTP_LOCALHOST_BASIC_URL + port + EXECUTE_ENDPOINT_PATH;
    }

    private void configureHeadersInRequest() {
        List<MediaType> acceptMediaType = new ArrayList<>();
        acceptMediaType.add(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptMediaType);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
}