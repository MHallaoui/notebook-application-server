package com.oracle.labs.notebookserver.exception;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class ExecutionTimeoutException extends Exception {
    public ExecutionTimeoutException(String cause) {
        super(cause);
    }
}
