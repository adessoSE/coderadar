package io.reflectoring.coderadar.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** A resource class used to return JSON Responses in the following format: { "id": 1} */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdResponse {
  private long id;
}
