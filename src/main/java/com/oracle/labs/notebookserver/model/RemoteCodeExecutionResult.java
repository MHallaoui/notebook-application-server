package com.oracle.labs.notebookserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Marouane EL HALLAOUI on 6/22/2019.
 */
public class RemoteCodeExecutionResult {
    @JsonProperty("result")
    private String result;

    public RemoteCodeExecutionResult(String result) {
        this.result = result;
    }
}
