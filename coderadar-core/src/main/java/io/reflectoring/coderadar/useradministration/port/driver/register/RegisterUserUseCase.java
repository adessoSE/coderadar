package io.reflectoring.coderadar.useradministration.port.driver.register;

import io.reflectoring.coderadar.useradministration.UsernameAlreadyInUseException;

public interface RegisterUserUseCase {
  Long register(RegisterUserCommand command) throws UsernameAlreadyInUseException;
}
