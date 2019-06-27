package io.reflectoring.coderadar.vcs.port.driver;

import java.nio.file.Path;

public interface CheckRepositoryUseCase {

  /**
   * @param folder The path to the local git repository
   * @return True if it points to a valid git repository, false otherwise.
   */
  boolean isRepository(Path folder);
}
