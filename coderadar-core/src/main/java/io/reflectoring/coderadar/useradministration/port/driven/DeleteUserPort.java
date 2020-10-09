package io.reflectoring.coderadar.useradministration.port.driven;

public interface DeleteUserPort {
  /**
   * Deletes a user. All projects the user has created are also deleted.
   *
   * @param userId The user id.
   */
  void deleteUser(long userId);
}
