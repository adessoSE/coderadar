package io.reflectoring.coderadar.projectadministration.domain;

import lombok.Data;

/** a user of application, who has to login to access to functionality */
@Data
public class User {
  private Long id;
  private String username;
  private String password;
}
