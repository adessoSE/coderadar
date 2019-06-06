package io.reflectoring.coderadar.projectadministration;

public class WrongPasswordException extends RuntimeException {
  public WrongPasswordException(String username) {
    super("Password for user " + username + " is wrong.");
  }
}
