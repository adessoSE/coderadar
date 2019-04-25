package io.reflectoring.coderadar.core.projectadministration.port.driver.user.login;

public interface LoginUserUseCase {
  LoginUserResponse login(LoginUserCommand command);
}
