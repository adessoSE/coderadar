package io.reflectoring.coderadar.core.projectadministration.port.driven.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;

import java.util.Optional;

public interface LoadUserPort {
  Optional<User> loadUser(Long id);

  Optional<User> loadUserByUsername(String username);
}
