package com.oracle.labs.notebookserver.exception;

/**
 * Created by EL HALLAOUI MAROUANE on 6/23/2019.
 */
public class ExecutionTimeOutException extends Exception {
    public ExecutionTimeOutException(String cause) {
        super(cause);
    }
}
