package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.projectadministration.EntityNotFoundException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ProjectIsBeingProcessedException.class)
    public ResponseEntity projectProcessingException(ProjectIsBeingProcessedException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
