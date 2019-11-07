package io.reflectoring.coderadar.useradministration;

import io.reflectoring.coderadar.projectadministration.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
  public UserNotFoundException(Long id) {
    super("User with id " + id + " not found.");
  }

  public UserNotFoundException(String username) {
    super("User with username " + username + " not found.");
  }
}
