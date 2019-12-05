package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.domain.DeltaTree;
import io.reflectoring.coderadar.query.port.driven.GetDeltaTreeForTwoCommitsPort;
import io.reflectoring.coderadar.query.port.driver.GetDeltaTreeForTwoCommitsCommand;
import io.reflectoring.coderadar.query.port.driver.GetDeltaTreeForTwoCommitsUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetDeltaTreeForTwoCommitsService implements GetDeltaTreeForTwoCommitsUseCase {

  private final GetDeltaTreeForTwoCommitsPort getDeltaTreeForTwoCommitsPort;

  public GetDeltaTreeForTwoCommitsService(
      GetDeltaTreeForTwoCommitsPort getDeltaTreeForTwoCommitsPort) {
    this.getDeltaTreeForTwoCommitsPort = getDeltaTreeForTwoCommitsPort;
  }

  @Override
  public DeltaTree get(GetDeltaTreeForTwoCommitsCommand command, Long projectId) {
    return getDeltaTreeForTwoCommitsPort.get(command, projectId);
  }
}
