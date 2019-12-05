package io.reflectoring.coderadar.vcs.port.driver.update;

import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;

public interface UpdateRepositoryUseCase {

  /**
   * Updates a local git repository (git pull against remote)
   *
   * @param command The command with the required parameters
   * @throws UnableToUpdateRepositoryException Thrown if there is an error while updating the
   *     repository.
   * @return Returns true if new commits were added and false otherwise
   */
  boolean updateRepository(UpdateRepositoryCommand command)
      throws UnableToUpdateRepositoryException;
}
