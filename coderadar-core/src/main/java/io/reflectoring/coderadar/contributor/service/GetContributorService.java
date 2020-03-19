package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import io.reflectoring.coderadar.contributor.port.driven.GetContributorPort;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetContributorService implements GetContributorUseCase {
  private final GetContributorPort getContributorPort;

  public GetContributorService(GetContributorPort getContributorPort) {
    this.getContributorPort = getContributorPort;
  }

  @Override
  public Contributor getById(long id) {
    return getContributorPort.get(id);
  }
}
