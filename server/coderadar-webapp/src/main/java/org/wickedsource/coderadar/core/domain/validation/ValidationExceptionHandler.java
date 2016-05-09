package org.wickedsource.coderadar.core.domain.validation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ValidationExceptionHandler {

    /**
     * Handles the MethodArgumentNotValidException that is thrown by Spring when a parameter passed into a controller
     * method is annotated with @Valid but fails the bean validation. Returns a ValidationErrorDTO containing the
     * validation error messages to the client.
     *
     * @param e the exception provided by Spring
     * @return ValidationErrorDTO object containing all field errors
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ValidationErrorsDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
        return map(fieldErrors);
    }

    private ValidationErrorsDTO map(List<org.springframework.validation.FieldError> fieldErrors) {
        ValidationErrorsDTO error = new ValidationErrorsDTO();
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            error.addFieldError(new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }
}
