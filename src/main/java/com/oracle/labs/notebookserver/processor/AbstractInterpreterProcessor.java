package com.oracle.labs.notebookserver.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptException;
import java.io.Writer;

import com.oracle.labs.notebookserver.exception.UnkownInterpreterType;
import com.oracle.labs.notebookserver.formatter.AbstractInterpreterResultFormatter;
import com.oracle.labs.notebookserver.model.AbstractInterpretationResult;

public abstract class AbstractInterpreterProcessor<ResultType extends AbstractInterpretationResult, ExecutionResultOutputWrapperType extends Writer> {

    protected Logger logger = LoggerFactory.getLogger(AbstractInterpreterProcessor.class);
    private ExecutionResultOutputWrapperType executionResultOutputWrapper;
    private AbstractInterpreterResultFormatter resultFormatter;

    protected AbstractInterpreterProcessor(final ExecutionResultOutputWrapperType resultOutputWrapper, final AbstractInterpreterResultFormatter formatter) {
        this.executionResultOutputWrapper = resultOutputWrapper;
        this.resultFormatter = formatter;
    }

    public abstract ResultType processScript(String jsCode) throws UnkownInterpreterType, ScriptException;

    protected ExecutionResultOutputWrapperType getExecutionResultOutputWrapper() {
        return executionResultOutputWrapper;
    }

    protected String formattedExecutionResult() {
        return resultFormatter.format(executionResultOutputWrapper.toString());
    }

    protected Logger getLogger() {
        return logger;
    }
}
