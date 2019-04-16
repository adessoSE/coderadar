package io.reflectoring.coderadar.core.projectadministration.port.driven.user;

import io.reflectoring.coderadar.core.projectadministration.domain.User;

public interface LoginUserPort {
  User login(User user);
}
