package io.reflectoring.coderadar.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
  private long id;
  private String username;
}
