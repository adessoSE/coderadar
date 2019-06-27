package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToProcessRepositoryException;
import io.reflectoring.coderadar.vcs.domain.CommitFilter;
import io.reflectoring.coderadar.vcs.domain.CommitProcessor;
import java.nio.file.Path;

public interface ProcessRepositoryPort {
  void processRepository(Path repositoryRoot, CommitProcessor processor, CommitFilter... filter)
      throws UnableToProcessRepositoryException;
}
