package io.reflectoring.coderadar.vcs;

public class UnableToFetchCommitException extends Exception {
  public UnableToFetchCommitException(String message) {
    super(message);
  }
}
