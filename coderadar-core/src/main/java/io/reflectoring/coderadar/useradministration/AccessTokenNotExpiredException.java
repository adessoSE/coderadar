package io.reflectoring.coderadar.useradministration;

public class AccessTokenNotExpiredException extends RuntimeException {

  public AccessTokenNotExpiredException() {
    super("Access token ist still valid. This token must be used for authentication.");
  }
}
