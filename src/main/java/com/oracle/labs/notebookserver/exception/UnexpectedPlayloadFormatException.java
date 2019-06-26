package com.oracle.labs.notebookserver.exception;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class UnexpectedPlayloadFormatException extends Exception {
    public UnexpectedPlayloadFormatException(String cause) {
        super(cause);
    }
}
