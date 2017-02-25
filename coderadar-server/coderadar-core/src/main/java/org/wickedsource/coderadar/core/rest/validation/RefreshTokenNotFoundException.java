package org.wickedsource.coderadar.core.rest.validation;

public class RefreshTokenNotFoundException extends UserException {

  public RefreshTokenNotFoundException() {
    super("Refresh token not found. New login is necessary.");
  }
}
