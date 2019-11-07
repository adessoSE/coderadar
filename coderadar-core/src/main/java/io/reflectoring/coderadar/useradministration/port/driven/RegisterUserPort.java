package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.domain.User;

public interface RegisterUserPort {
  Long register(User user);
}
