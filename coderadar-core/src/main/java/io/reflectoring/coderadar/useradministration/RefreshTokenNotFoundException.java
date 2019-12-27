package io.reflectoring.coderadar.useradministration;

public class RefreshTokenNotFoundException extends RuntimeException {

  public RefreshTokenNotFoundException() {
    super("Refresh token not found. New login is necessary.");
  }
}
