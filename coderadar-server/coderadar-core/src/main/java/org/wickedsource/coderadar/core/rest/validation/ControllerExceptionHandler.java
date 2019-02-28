package org.wickedsource.coderadar.core.rest.validation;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.wickedsource.coderadar.core.common.ResourceNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

  private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  /**
   * Handles the MethodArgumentNotValidException that is thrown by Spring when a parameter passed
   * into a controller method is annotated with @Valid but fails the bean validation. Returns a
   * ValidationErrorDTO containing the validation error messages to the client.
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
  public ErrorDTO handleValidationException(ValidationException e) {
    ErrorDTO errors = new ErrorDTO();
    FieldErrorDTO fieldError = new FieldErrorDTO(e.getField(), e.getValidationMessage());
    errors.addFieldError(fieldError);
    errors.setErrorMessage("Validation Error");
    return errors;
  }

  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(UserException.class)
  @ResponseBody
  public ErrorDTO handleUserException(UserException e) {
    ErrorDTO errors = new ErrorDTO();
    errors.setErrorMessage(e.getMessage());
    return errors;
  }

  @ResponseStatus(value = HttpStatus.CONFLICT)
  @ExceptionHandler(RegistrationException.class)
  @ResponseBody
  public ErrorDTO handleRegistrationException(RegistrationException e) {
    ErrorDTO errors = new ErrorDTO();
    errors.setErrorMessage(e.getMessage());
    return errors;
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseBody
  public ErrorDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    ErrorDTO errors = new ErrorDTO();
    errors.setErrorMessage(e.getMessage());
    return errors;
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseBody
  public ErrorDTO handleInvalidRequestMethod(HttpRequestMethodNotSupportedException e) {
    ErrorDTO errors = new ErrorDTO();
    errors.setErrorMessage(e.getMessage());
    return errors;
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseBody
  public ErrorDTO handleInvalidContentTypeException(HttpMediaTypeNotSupportedException e) {
    ErrorDTO errors = new ErrorDTO();
    errors.setErrorMessage(e.getMessage());
    return errors;
  }

  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseBody
  public String handleNotFound(ResourceNotFoundException e) {
    if (e.getMessage() == null) {
      return "Resource not found!";
    } else {
      return e.getMessage();
    }
  }

  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  @ResponseBody
  public String handleInternalError(Exception e) {
    logger.error("Returned HTTP Status 500 due to the following exception:", e);
    return "Internal Server Error";
  }

  private ErrorDTO map(List<org.springframework.validation.FieldError> fieldErrors) {
    ErrorDTO error = new ErrorDTO();
    for (org.springframework.validation.FieldError fieldError : fieldErrors) {
      error.addFieldError(new FieldErrorDTO(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    error.setErrorMessage("Validation Error");
    return error;
  }
}
