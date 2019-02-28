package org.wickedsource.coderadar.core.common;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException() {
    super((String) null);
  }

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
