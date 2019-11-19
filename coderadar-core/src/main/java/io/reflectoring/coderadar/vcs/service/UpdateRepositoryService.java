package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.UpdateRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.UpdateRepositoryUseCase;
import java.net.URL;
import java.nio.file.Path;
import org.springframework.stereotype.Service;

@Service
public class UpdateRepositoryService implements UpdateRepositoryUseCase {

  private final UpdateRepositoryPort updateRepositoryPort;

  public UpdateRepositoryService(UpdateRepositoryPort updateRepositoryPort) {
    this.updateRepositoryPort = updateRepositoryPort;
  }

  @Override
  public boolean updateRepository(Path repositoryRoot, URL url)
      throws UnableToUpdateRepositoryException {
    return updateRepositoryPort.updateRepository(repositoryRoot, url);
  }
}
