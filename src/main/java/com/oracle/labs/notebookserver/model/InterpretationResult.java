package com.oracle.labs.notebookserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Marouane EL HALLAOUI on 6/22/2019.
 */
public class InterpretationResult implements AbstractInterpretationResult {
    @JsonProperty("result")
    private String result;

    public InterpretationResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
