package com.oracle.labs.notebookserver.endpoint;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.labs.notebookserver.model.PlayLoad;
import com.oracle.labs.notebookserver.model.RemoteCodeExecutionResult;

/**
 * Created by Marouane EL HALLAOUI on 6/22/2019.
 */

@RestController
public class NoteBookEndpoint {

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public RemoteCodeExecutionResult evaluate(@RequestBody PlayLoad code) {
        return new RemoteCodeExecutionResult(code.getCode());
    }
}
