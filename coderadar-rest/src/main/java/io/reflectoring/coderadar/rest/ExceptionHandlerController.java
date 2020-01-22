package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.EntityAlreadyExistsException;
import io.reflectoring.coderadar.EntityNotFoundException;
import io.reflectoring.coderadar.analyzer.MisconfigurationException;
import io.reflectoring.coderadar.plugin.api.AnalyzerConfigurationException;
import io.reflectoring.coderadar.projectadministration.ModulePathInvalidException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ProjectIsBeingProcessedException.class)
    public ResponseEntity<ErrorMessageResponse> projectProcessingException(ProjectIsBeingProcessedException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> entityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorMessageResponse> entityAlreadyExistsException(EntityAlreadyExistsException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MisconfigurationException.class)
    public ResponseEntity<ErrorMessageResponse> misconfigurationException(MisconfigurationException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AnalyzerConfigurationException.class)
    public ResponseEntity<ErrorMessageResponse> analyzerConfigurationException(AnalyzerConfigurationException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ModulePathInvalidException.class)
    public ResponseEntity<ErrorMessageResponse> modulePathInvalidException(ModulePathInvalidException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessageResponse> illegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessageResponse> authenticationException(AuthenticationException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }
}
