package io.reflectoring.coderadar.vcs.port.driver.clone;

import io.reflectoring.coderadar.vcs.UnableToCloneRepositoryException;

public interface CloneRepositoryUseCase {

  /**
   * Clones a remote git repository to the local working directory
   *
   * @param command A CloneRepositoryCommand containing the needed arguments.
   * @throws UnableToCloneRepositoryException Thrown if there is an error while cloning the
   *     repository.
   */
  void cloneRepository(CloneRepositoryCommand command) throws UnableToCloneRepositoryException;
}
