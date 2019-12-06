package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;
import io.reflectoring.coderadar.vcs.port.driver.clone.CloneRepositoryCommand;

public interface CloneRepositoryPort {

  /**
   * Clones a remote git repository to the local working directory
   *
   * @param cloneRepositoryCommand The command containing all parameters needed to clone
   * @throws UnableToCloneRepositoryException Thrown if there is an error while cloning the
   *     repository.
   */
  void cloneRepository(CloneRepositoryCommand cloneRepositoryCommand)
      throws UnableToCloneRepositoryException;
}
