package io.reflectoring.coderadar.query.port.driver;

import io.reflectoring.coderadar.query.domain.DeltaTree;

public interface GetMetricValuesOfTwoCommitsUseCase {
  DeltaTree get(GetMetricsForTwoCommitsCommand command, Long projectId);
}
