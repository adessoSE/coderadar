package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.port.driven.UpdateContributorPort;
import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorCommand;
import io.reflectoring.coderadar.contributor.port.driver.UpdateContributorUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateContributorService implements UpdateContributorUseCase {
  private final UpdateContributorPort updateContributorPort;
  private static final Logger logger = LoggerFactory.getLogger(UpdateContributorService.class);

  @Override
  public void updateContributor(long id, UpdateContributorCommand command) {
    updateContributorPort.updateContributor(id, command);
    logger.info(
        "Updated contributor with id {}. New display name: {}", id, command.getDisplayName());
  }
}
