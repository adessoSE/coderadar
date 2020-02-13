package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.port.driven.MergeContributorsPort;
import io.reflectoring.coderadar.contributor.port.driver.MergeContributorsCommand;
import io.reflectoring.coderadar.contributor.port.driver.MergeContributorsUseCase;
import org.springframework.stereotype.Service;

@Service
public class MergeContributorsService implements MergeContributorsUseCase {
  private final MergeContributorsPort mergeContributorsPort;

  public MergeContributorsService(MergeContributorsPort mergeContributorsPort) {
    this.mergeContributorsPort = mergeContributorsPort;
  }

  @Override
  public void mergeContributors(MergeContributorsCommand command) {
    mergeContributorsPort.mergeContributors(
        command.getFirstContributorId(), command.getSecondContributorId());
  }
}
