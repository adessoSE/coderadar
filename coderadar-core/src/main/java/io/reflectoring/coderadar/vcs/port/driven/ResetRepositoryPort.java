package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToResetRepositoryException;
import java.nio.file.Path;

public interface ResetRepositoryPort {

  /**
   * Resets a local git repository.
   *
   * @param repositoryRoot The path of the repository to reset/
   */
  void resetRepository(Path repositoryRoot) throws UnableToResetRepositoryException;
}
