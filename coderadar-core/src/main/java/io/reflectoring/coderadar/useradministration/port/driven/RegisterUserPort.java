package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.domain.User;

public interface RegisterUserPort {

  /**
   * Registers (Creates) a new user.
   *
   * @param user The user to save.
   * @return The DB id of the user.
   */
  long register(User user);
}
