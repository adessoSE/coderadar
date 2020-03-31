package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.port.driven.MergeContributorsPort;
import io.reflectoring.coderadar.contributor.port.driver.MergeContributorsCommand;
import io.reflectoring.coderadar.contributor.port.driver.MergeContributorsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MergeContributorsService implements MergeContributorsUseCase {
  private final MergeContributorsPort mergeContributorsPort;
  private final Logger logger = LoggerFactory.getLogger(MergeContributorsService.class);

  public MergeContributorsService(MergeContributorsPort mergeContributorsPort) {
    this.mergeContributorsPort = mergeContributorsPort;
  }

  @Override
  public void mergeContributors(MergeContributorsCommand command) {
    mergeContributorsPort.mergeContributors(command.getContributorIds(), command.getDisplayName());
    logger.info("Merged contributors with ids {}", command.getContributorIds());
  }
}
