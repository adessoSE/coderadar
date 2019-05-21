package io.reflectoring.coderadar.core.projectadministration.port.driver.user.register;

import io.reflectoring.coderadar.core.projectadministration.UsernameAlreadyInUseException;

public interface RegisterUserUseCase {
  Long register(RegisterUserCommand command) throws UsernameAlreadyInUseException;
}
