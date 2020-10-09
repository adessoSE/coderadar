package io.reflectoring.coderadar.useradministration.port.driver.delete;

public interface DeleteUserUseCase {

  /**
   * Deletes a user. All projects the user has created are also deleted.
   *
   * @param userId The user id.
   */
  void deleteUser(long userId);
}
