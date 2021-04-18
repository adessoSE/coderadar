package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.domain.DeltaTree;
import io.reflectoring.coderadar.query.port.driven.GetDeltaTreeForTwoCommitsPort;
import io.reflectoring.coderadar.query.port.driver.deltatree.GetDeltaTreeForTwoCommitsCommand;
import io.reflectoring.coderadar.query.port.driver.deltatree.GetDeltaTreeForTwoCommitsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetDeltaTreeForTwoCommitsService implements GetDeltaTreeForTwoCommitsUseCase {

  private final GetDeltaTreeForTwoCommitsPort getDeltaTreeForTwoCommitsPort;

  @Override
  public DeltaTree get(GetDeltaTreeForTwoCommitsCommand command, long projectId) {
    return getDeltaTreeForTwoCommitsPort.get(command, projectId);
  }
}
