package io.reflectoring.coderadar.core.projectadministration;


public class TokenExpiresException extends RuntimeException {

  public TokenExpiresException() {
    super("Access token is expired");
  }
}
