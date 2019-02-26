package org.wickedsource.coderadar.user.rest;

import javax.validation.constraints.NotNull;

public class UserResource {

  private Long id;

  @NotNull private String username;

  public UserResource() {}

  public UserResource(Long id, String username) {
    this.id = id;
    this.username = username;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
