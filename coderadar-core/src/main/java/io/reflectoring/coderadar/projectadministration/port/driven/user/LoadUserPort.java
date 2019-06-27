package io.reflectoring.coderadar.projectadministration.port.driven.user;

import io.reflectoring.coderadar.projectadministration.UserNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.User;

public interface LoadUserPort {
  User loadUser(Long id) throws UserNotFoundException;

  User loadUserByUsername(String username) throws UserNotFoundException;

  boolean existsByUsername(String username);
}
