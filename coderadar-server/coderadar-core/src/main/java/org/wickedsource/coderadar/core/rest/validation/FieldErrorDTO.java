package org.wickedsource.coderadar.core.rest.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor // Default constructor is needed for JSON mapping.
public class FieldErrorDTO {

  private String field;

  private String message;

  public String getField() {
    return field;
  }

  public String getMessage() {
    return message;
  }
}
