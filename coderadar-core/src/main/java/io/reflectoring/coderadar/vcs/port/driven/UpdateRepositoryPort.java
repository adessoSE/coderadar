package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import java.nio.file.Path;

public interface UpdateRepositoryPort {

  void updateRepository(Path repositoryRoot) throws UnableToUpdateRepositoryException;
}
