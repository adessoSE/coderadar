package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.port.driven.UpdateContributorPort;
import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorCommand;
import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorUseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateContributorService implements UpdateContributorUseCase {
  private final UpdateContributorPort updateContributorPort;

  public UpdateContributorService(UpdateContributorPort updateContributorPort) {
    this.updateContributorPort = updateContributorPort;
  }

  @Override
  public void updateContributor(Long id, UpdateContributorCommand command) {
    updateContributorPort.updateContributor(id, command);
  }
}
