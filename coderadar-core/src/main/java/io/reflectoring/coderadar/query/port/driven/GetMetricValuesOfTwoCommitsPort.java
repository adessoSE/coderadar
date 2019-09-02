package io.reflectoring.coderadar.query.port.driven;

import io.reflectoring.coderadar.query.domain.DeltaTree;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForTwoCommitsCommand;

public interface GetMetricValuesOfTwoCommitsPort {
  DeltaTree get(GetMetricsForTwoCommitsCommand command, Long projectId);
}
