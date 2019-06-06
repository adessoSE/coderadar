package io.reflectoring.coderadar.projectadministration.port.driver.user.login;

public interface LoginUserUseCase {
  LoginUserResponse login(LoginUserCommand command);
}
