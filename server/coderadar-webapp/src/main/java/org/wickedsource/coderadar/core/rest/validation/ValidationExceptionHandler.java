package org.wickedsource.coderadar.core.rest.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(ValidationExceptionHandler.class);

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
    public ErrorDTO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
        return map(fieldErrors);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ErrorDTO handleValidationException(ValidationException e){
        ErrorDTO errors = new ErrorDTO();
        FieldErrorDTO fieldError = new FieldErrorDTO(e.getField(), e.getValidationMessage());
        errors.addFieldError(fieldError);
        return errors;
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorDTO handleInternalError(Exception e){
        logger.error("Returned HTTP Status 500 due to the following exception:", e);
        ErrorDTO error = new ErrorDTO();
        error.setMessage("Internal Server Error.");
        return error;
    }

    private ErrorDTO map(List<org.springframework.validation.FieldError> fieldErrors) {
        ErrorDTO error = new ErrorDTO();
        for (org.springframework.validation.FieldError fieldError : fieldErrors) {
            error.addFieldError(new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }
}
