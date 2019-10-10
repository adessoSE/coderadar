package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import java.net.URL;
import java.nio.file.Path;

public interface UpdateRepositoryPort {

  boolean updateRepository(Path repositoryRoot, URL url) throws UnableToUpdateRepositoryException;
}
