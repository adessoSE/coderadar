package io.reflectoring.coderadar.useradministration.port.driver.load;

import io.reflectoring.coderadar.useradministration.domain.User;

public interface LoadUserUseCase {

  /**
   * Retrieves a user given their id.
   *
   * @param id The id of the user.
   * @return The user with the id.
   */
  User loadUser(Long id);
}
