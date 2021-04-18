package io.reflectoring.coderadar.contributor.service;

import io.reflectoring.coderadar.contributor.port.driven.GetContributorPort;
import io.reflectoring.coderadar.contributor.port.driver.GetContributorUseCase;
import io.reflectoring.coderadar.domain.Contributor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetContributorService implements GetContributorUseCase {
  private final GetContributorPort getContributorPort;

  @Override
  public Contributor getById(long id) {
    return getContributorPort.get(id);
  }
}
