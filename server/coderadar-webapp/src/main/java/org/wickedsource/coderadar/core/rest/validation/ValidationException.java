package org.wickedsource.coderadar.core.rest.validation;

public class ValidationException extends RuntimeException {

    private final String field;

    private final String validationMessage;

    public ValidationException(String field, String validationMessage) {
        this.field = field;
        this.validationMessage = validationMessage;
    }

    public String getField() {
        return field;
    }

    public String getValidationMessage() {
        return validationMessage;
    }
}
