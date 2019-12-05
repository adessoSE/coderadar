package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.DeltaTree;

public interface GetDeltaTreeForTwoCommitsUseCase {
  DeltaTree get(GetDeltaTreeForTwoCommitsCommand command, Long projectId);
}
