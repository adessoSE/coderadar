package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.domain.User;

public interface GetUserPort {

  /**
   * Retrieves a user given their id.
   *
   * @param id The id of the user.
   * @return The user with the id.
   */
  User getUser(long id);

  /**
   * Retrieves a user given their username.
   *
   * @param username The username of the user.
   * @return The user with the username.
   */
  User getUserByUsername(String username);

  /**
   * @param username The username of the user.
   * @return True if a user with this username exists, false otherwise.
   */
  boolean existsByUsername(String username);

  /**
   * @param id The id of the user.
   * @return True if a user with this id exists, false otherwise.
   */
  boolean existsById(long id);
}
