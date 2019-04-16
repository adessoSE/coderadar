package io.reflectoring.coderadar.core.query.domain;

public class ResourceNotFoundException extends RuntimeException {
  public ResourceNotFoundException() {
    super((String) null);
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
