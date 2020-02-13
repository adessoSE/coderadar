package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.GetContributorPort;
import io.reflectoring.coderadar.contributor.port.driven.MergeContributorsPort;
import io.reflectoring.coderadar.contributor.port.driver.MergeContributorsCommand;
import io.reflectoring.coderadar.contributor.port.driver.MergeContributorsUseCase;
import org.springframework.stereotype.Service;

@Service
public class MergeContributorsService implements MergeContributorsUseCase {
  private final MergeContributorsPort mergeContributorsPort;
  private final GetContributorPort getContributorPort;

  public MergeContributorsService(
      MergeContributorsPort mergeContributorsPort, GetContributorPort getContributorPort) {
    this.mergeContributorsPort = mergeContributorsPort;
    this.getContributorPort = getContributorPort;
  }

  @Override
  public void mergeContributors(MergeContributorsCommand command) {
    Contributor first = getContributorPort.getContributorById(command.getFirstContributorId());
    Contributor second = getContributorPort.getContributorById(command.getSecondContributorId());
    mergeContributorsPort.mergeContributors(first, second);
  }
}
