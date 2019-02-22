package org.wickedsource.coderadar.user.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserLoginResource {

  @NotNull private String username;

  @NotNull
  @Size(min = 8)
  private String password;

  public UserLoginResource() {}

  public UserLoginResource(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
