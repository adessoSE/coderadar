package org.wickedsource.coderadar.core.configuration.configparams;

public class ParameterValidationError {

    private final String message;

    private Exception exception;

    public ParameterValidationError(String message) {
        this.message = message;
    }

    public ParameterValidationError(String message, Exception e) {
        this.message = message;
        this.exception = e;
    }

    public String getMessage() {
        return message;
    }

    public Exception getException() {
        return exception;
    }
}
