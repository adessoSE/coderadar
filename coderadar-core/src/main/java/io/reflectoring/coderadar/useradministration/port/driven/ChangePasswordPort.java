package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.domain.User;

public interface ChangePasswordPort {

  /**
   * Changes the password of a user.
   *
   * @param user The user with the changed password.
   */
  void changePassword(User user);
}
