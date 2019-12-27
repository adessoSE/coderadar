package io.reflectoring.coderadar.projectadministration;

import io.reflectoring.coderadar.EntityNotFoundException;

public class CommitNotFoundException extends EntityNotFoundException {
  public CommitNotFoundException(String hash) {
    super(String.format("Commit with hash %s not found!", hash));
  }
}
