package org.wickedsource.coderadar.core.rest.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorDTO {

    private List<FieldErrorDTO> fieldErrors = new ArrayList<>();

    private String message;

    public void addFieldError(FieldErrorDTO fieldError) {
        this.fieldErrors.add(fieldError);
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    public List<FieldErrorDTO> getErrorsForField(String fieldName) {
        return this.fieldErrors.stream().filter(error -> error.getField().equals(fieldName)).collect(Collectors.toList());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
