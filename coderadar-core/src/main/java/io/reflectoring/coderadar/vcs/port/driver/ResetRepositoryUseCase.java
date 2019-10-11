package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.vcs.UnableToResetRepositoryException;
import java.nio.file.Path;

public interface ResetRepositoryUseCase {

  /**
   * Resets a local git repository.
   *
   * @param repositoryRoot The path of the repository to reset/
   */
  void resetRepository(Path repositoryRoot) throws UnableToResetRepositoryException;
}
