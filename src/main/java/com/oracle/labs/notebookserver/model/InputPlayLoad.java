package com.oracle.labs.notebookserver.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by EL HALLAOUI MAROUANE on 6/22/2019.
 */
public class InputPlayload {

    @JsonProperty("code")
    private String code;

    public String getCode() {
        return code;
    }
}
