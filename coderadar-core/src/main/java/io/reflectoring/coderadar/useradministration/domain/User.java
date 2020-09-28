package io.reflectoring.coderadar.useradministration.domain;

import lombok.Data;

/** User of the application. Has to login to access to functionality */
@Data
public class User {
  private long id;
  private String username;
  private String password;
  private boolean platformAdmin = false;
}
