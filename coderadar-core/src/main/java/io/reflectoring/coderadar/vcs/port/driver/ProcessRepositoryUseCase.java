package io.reflectoring.coderadar.vcs.port.driver;

import io.reflectoring.coderadar.vcs.UnableToProcessRepositoryException;
import io.reflectoring.coderadar.vcs.domain.CommitFilter;
import io.reflectoring.coderadar.vcs.domain.CommitProcessor;

import java.nio.file.Path;

public interface ProcessRepositoryUseCase {
  void processRepository(Path repositoryRoot, CommitProcessor processor, CommitFilter... filter)
      throws UnableToProcessRepositoryException;
}
