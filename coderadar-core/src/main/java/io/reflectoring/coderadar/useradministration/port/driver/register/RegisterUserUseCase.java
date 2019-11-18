package io.reflectoring.coderadar.useradministration.port.driver.register;

import io.reflectoring.coderadar.useradministration.UsernameAlreadyInUseException;

public interface RegisterUserUseCase {

  /**
   * Registers (Creates) a new user.
   *
   * @param command The user to register.
   * @return The DB id of the user.
   */
  Long register(RegisterUserCommand command) throws UsernameAlreadyInUseException;
}
