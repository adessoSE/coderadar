package io.reflectoring.coderadar.query.port.driver;

public interface GetMetricValuesOfTwoCommitsUseCase {
  DeltaTree get(GetMetricsForTwoCommitsCommand command, Long projectId);
}
