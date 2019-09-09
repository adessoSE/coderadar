package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.UpdateRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateRepositoryService implements UpdateRepositoryUseCase {

  private final UpdateRepositoryPort updateRepositoryPort;

  @Autowired
  public UpdateRepositoryService(UpdateRepositoryPort updateRepositoryPort) {
    this.updateRepositoryPort = updateRepositoryPort;
  }

  @Override
  public boolean updateRepository(Path repositoryRoot) throws UnableToUpdateRepositoryException {
    return updateRepositoryPort.updateRepository(repositoryRoot);
  }
}
