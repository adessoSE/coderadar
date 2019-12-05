package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.DeltaTree;
import io.reflectoring.coderadar.query.port.driver.GetDeltaTreeForTwoCommitsCommand;

public interface GetDeltaTreeForTwoCommitsPort {
  DeltaTree get(GetDeltaTreeForTwoCommitsCommand command, Long projectId);
}
