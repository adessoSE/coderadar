package org.wickedsource.coderadar.core.rest.validation;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorDTO {

    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<FieldErrorDTO> fieldErrors;

    public void addFieldError(FieldErrorDTO fieldError) {
        if(fieldErrors == null){
            this.fieldErrors = new ArrayList<>();
        }
        this.fieldErrors.add(fieldError);
    }

    public List<FieldErrorDTO> getFieldErrors() {
        return fieldErrors;
    }

    public List<FieldErrorDTO> getErrorsForField(String fieldName) {
        if(this.fieldErrors != null) {
            return this.fieldErrors.stream().filter(error -> error.getField().equals(fieldName)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
