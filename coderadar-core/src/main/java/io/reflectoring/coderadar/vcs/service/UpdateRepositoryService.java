package io.reflectoring.coderadar.vcs.service;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.vcs.UnableToUpdateRepositoryException;
import io.reflectoring.coderadar.vcs.port.driven.UpdateRepositoryPort;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryCommand;
import io.reflectoring.coderadar.vcs.port.driver.update.UpdateRepositoryUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UpdateRepositoryService implements UpdateRepositoryUseCase {

  private final UpdateRepositoryPort updateRepositoryPort;

  public UpdateRepositoryService(UpdateRepositoryPort updateRepositoryPort) {
    this.updateRepositoryPort = updateRepositoryPort;
  }

  @Override
  public List<Branch> updateRepository(UpdateRepositoryCommand command)
      throws UnableToUpdateRepositoryException {
    return updateRepositoryPort.updateRepository(command);
  }
}
