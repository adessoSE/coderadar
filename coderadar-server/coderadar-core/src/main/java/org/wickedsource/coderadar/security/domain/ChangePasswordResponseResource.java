package org.wickedsource.coderadar.security.domain;

public class ChangePasswordResponseResource {

  // indicates, whether the password change was successful or not
  private boolean successful = true;

  public ChangePasswordResponseResource() {}

  public boolean isSuccessful() {
    return successful;
  }

  public void setSuccessful(boolean successful) {
    this.successful = successful;
  }
}
