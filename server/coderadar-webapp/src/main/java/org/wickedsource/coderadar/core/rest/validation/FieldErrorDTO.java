package org.wickedsource.coderadar.core.rest.validation;

public class FieldErrorDTO {

  private String field;

  private String message;

  /** Default constructor is needed for JSON mapping. */
  public FieldErrorDTO() {}

  public FieldErrorDTO(String field, String message) {
    this.field = field;
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public String getMessage() {
    return message;
  }
}
