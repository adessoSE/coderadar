package io.reflectoring.coderadar.core.query.domain;

public class AccessTokenNotExpiredException extends UserException {

  public AccessTokenNotExpiredException() {
    super("Access token ist still valid. This token must be used for authentication.");
  }
}
