package io.reflectoring.coderadar.core.projectadministration;

public class AccessTokenNotExpiredException extends RuntimeException {

  public AccessTokenNotExpiredException() {
    super("Access token ist still valid. This token must be used for authentication.");
  }
}
