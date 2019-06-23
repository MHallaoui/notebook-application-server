package com.oracle.labs.notebookserver.parser;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class ParserResult {

    private String rawCode;
    private String interpreterType;

    public ParserResult(String rawCode, String interpreterType) {
        this.rawCode = rawCode;
        this.interpreterType = interpreterType;
    }

    public String getRawCode() {
        return rawCode;
    }

    public void setRawCode(String rawCode) {
        this.rawCode = rawCode;
    }

    public String getInterpreterType() {
        return interpreterType;
    }

    public void setInterpreterType(String interpreterType) {
        this.interpreterType = interpreterType;
    }
}
