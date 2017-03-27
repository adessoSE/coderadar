package org.wickedsource.coderadar.security.domain;

import javax.validation.constraints.NotNull;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.security.ValidPassword;

public class PasswordChangeResource extends ResourceSupport {

  @NotNull private String refreshToken;

  @ValidPassword private String newPassword;

  public PasswordChangeResource() {}

  public PasswordChangeResource(String refreshToken, String newPassword) {
    this.refreshToken = refreshToken;
    this.newPassword = newPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
