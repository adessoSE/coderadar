package org.wickedsource.coderadar.user.domain;

import javax.validation.constraints.NotNull;

public class UserResource {

  @NotNull private String username;

  public UserResource() {}

  public UserResource(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
