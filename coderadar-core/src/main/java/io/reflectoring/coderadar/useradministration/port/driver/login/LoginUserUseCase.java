package io.reflectoring.coderadar.useradministration.port.driver.login;

public interface LoginUserUseCase {
  LoginUserResponse login(LoginUserCommand command);
}
