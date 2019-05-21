package io.reflectoring.coderadar.core.projectadministration.service.user;


public class TokenExpiresException extends RuntimeException {

  public TokenExpiresException() {
    super("Access token is expired");
  }
}
