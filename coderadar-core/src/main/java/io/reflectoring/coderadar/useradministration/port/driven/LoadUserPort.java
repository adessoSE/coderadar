package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.User;

public interface LoadUserPort {
  User loadUser(Long id) throws UserNotFoundException;

  User loadUserByUsername(String username) throws UserNotFoundException;

  boolean existsByUsername(String username);
}
