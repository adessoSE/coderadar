package io.reflectoring.coderadar.useradministration.port.driver.get;

import io.reflectoring.coderadar.domain.User;

public interface GetUserUseCase {

  /**
   * Retrieves a user given their id.
   *
   * @param id The id of the user.
   * @return The user with the id.
   */
  User getUser(long id);
}
