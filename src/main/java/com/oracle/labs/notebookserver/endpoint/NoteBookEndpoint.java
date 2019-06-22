package com.oracle.labs.notebookserver.endpoint;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;


import com.oracle.labs.notebookserver.model.RemoteCodeExecutionResult;
import com.oracle.labs.notebookserver.model.UserCodePlayLoad;

/**
 * Created by Marouane EL HALLAOUI on 6/22/2019.
 */

@RestController
public class NoteBookEndpoint {

    @RequestMapping(value = "/execute", method = RequestMethod.POST)
    public RemoteCodeExecutionResult evaluate(@RequestBody UserCodePlayLoad code) {

        Context jsContext = Context.create("js");
        Value jsEvaluationResult = runScript("1+1", jsContext);

        return new RemoteCodeExecutionResult(jsEvaluationResult.toString());
    }

    private Value runScript(String script, Context context) {
        return context.eval("js", script);
    }
}
