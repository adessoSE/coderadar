package io.reflectoring.coderadar.useradministration.port.driver.login;

public interface LoginUserUseCase {

  /**
   * Logs a user in to coderadar.
   *
   * @param command The login command containing credentials.
   * @return The response containing access tokens.
   */
  LoginUserResponse login(LoginUserCommand command);
}
