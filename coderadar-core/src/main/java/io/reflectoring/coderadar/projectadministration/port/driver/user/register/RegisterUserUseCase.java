package io.reflectoring.coderadar.projectadministration.port.driver.user.register;

import io.reflectoring.coderadar.projectadministration.UsernameAlreadyInUseException;

public interface RegisterUserUseCase {
  Long register(RegisterUserCommand command) throws UsernameAlreadyInUseException;
}
