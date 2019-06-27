package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.UnableToProcessRepositoryException;
import io.reflectoring.coderadar.vcs.domain.CommitFilter;
import io.reflectoring.coderadar.vcs.domain.CommitProcessor;
import io.reflectoring.coderadar.vcs.port.driven.ProcessRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.ProcessRepositoryUseCase;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessRepositoryService implements ProcessRepositoryUseCase {

  private final ProcessRepositoryPort processRepositoryPort;

  @Autowired
  public ProcessRepositoryService(ProcessRepositoryPort processRepositoryPort) {
    this.processRepositoryPort = processRepositoryPort;
  }

  @Override
  public void processRepository(
      Path repositoryRoot, CommitProcessor processor, CommitFilter... filter)
      throws UnableToProcessRepositoryException {
    processRepositoryPort.processRepository(repositoryRoot, processor, filter);
  }
}
