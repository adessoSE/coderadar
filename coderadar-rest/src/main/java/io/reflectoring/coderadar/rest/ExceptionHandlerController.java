package io.reflectoring.coderadar.rest;

import io.reflectoring.coderadar.EntityAlreadyExistsException;
import io.reflectoring.coderadar.EntityNotFoundException;
import io.reflectoring.coderadar.analyzer.MisconfigurationException;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.rest.domain.ErrorMessageResponse;
import io.reflectoring.coderadar.useradministration.service.UserUnauthenticatedException;
import io.reflectoring.coderadar.useradministration.service.UserUnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(ProjectIsBeingProcessedException.class)
  public ResponseEntity<ErrorMessageResponse> projectProcessingException(
      ProjectIsBeingProcessedException e) {
    return new ResponseEntity<>(
        new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(UserUnauthenticatedException.class)
  public ResponseEntity<ErrorMessageResponse> userUnauthenticatedException(
      UserUnauthenticatedException e) {
    return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UserUnauthorizedException.class)
  public ResponseEntity<ErrorMessageResponse> userUnauthorizedException(
      UserUnauthorizedException e) {
    return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorMessageResponse> entityNotFoundException(EntityNotFoundException e) {
    return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(EntityAlreadyExistsException.class)
  public ResponseEntity<ErrorMessageResponse> entityAlreadyExistsException(
      EntityAlreadyExistsException e) {
    return new ResponseEntity<>(new ErrorMessageResponse(e.getMessage()), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(MisconfigurationException.class)
  public ResponseEntity<ErrorMessageResponse> misconfigurationException(
      MisconfigurationException e) {
    return new ResponseEntity<>(
        new ErrorMessageResponse(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorMessageResponse> illegalArgumentException(
          IllegalArgumentException e) {
    return new ResponseEntity<>(
            new ErrorMessageResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
  }
}
