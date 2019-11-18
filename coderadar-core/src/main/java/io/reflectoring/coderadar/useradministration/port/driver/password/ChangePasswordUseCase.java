package io.reflectoring.coderadar.useradministration.port.driver.password;

public interface ChangePasswordUseCase {

  /**
   * Changes the password of a user.
   *
   * @param command The command containing the changed password.
   */
  void changePassword(ChangePasswordCommand command);
}
