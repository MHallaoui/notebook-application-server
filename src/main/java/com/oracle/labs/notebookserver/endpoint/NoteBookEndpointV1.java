package com.oracle.labs.notebookserver.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.labs.notebookserver.endpoint.handler.EndpointRequestHandler;
import com.oracle.labs.notebookserver.model.InputPlayLoad;

/**
 * Created by Marouane EL HALLAOUI on 6/22/2019.
 */

@RestController
public class NoteBookEndpointV1 {

    @Autowired
    private EndpointRequestHandler requestHandler;

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public ResponseEntity evaluate(@RequestBody InputPlayLoad request) {

        return requestHandler.processRequest(request);
    }
}
