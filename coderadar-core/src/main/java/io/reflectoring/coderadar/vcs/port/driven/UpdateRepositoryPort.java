package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import java.nio.file.Path;

public interface UpdateRepositoryPort {

  boolean updateRepository(Path repositoryRoot) throws UnableToUpdateRepositoryException;
}
