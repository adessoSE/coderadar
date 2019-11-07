package io.reflectoring.coderadar.useradministration;

public class TokenExpiresException extends RuntimeException {

  public TokenExpiresException() {
    super("Access token is expired");
  }
}
