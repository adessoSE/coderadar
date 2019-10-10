package io.reflectoring.coderadar.projectadministration;

public class TokenExpiresException extends RuntimeException {

  public TokenExpiresException() {
    super("Access token is expired");
  }
}
