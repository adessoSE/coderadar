package io.reflectoring.coderadar.useradministration.port.driver.load;

public interface LoadUserUseCase {

  /**
   * Retrieves a user given their id.
   *
   * @param id The id of the user.
   * @return The user with the id.
   */
  LoadUserResponse loadUser(Long id);
}
