package org.wickedsource.coderadar.security.domain;

import javax.validation.constraints.NotNull;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.security.ValidPassword;

public class PasswordChangeResource extends ResourceSupport {

  @NotNull private String username;

  @ValidPassword private String newPassword;

  public PasswordChangeResource() {}

  public PasswordChangeResource(String username, String newPassword) {
    this.username = username;
    this.newPassword = newPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
