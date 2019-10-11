package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.query.domain.DeltaTree;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfTwoCommitsPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricValuesOfTwoCommitsUseCase;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForTwoCommitsCommand;
import org.springframework.stereotype.Service;

@Service
public class GetMetricValuesOfTwoCommitsService implements GetMetricValuesOfTwoCommitsUseCase {

  private final GetMetricValuesOfTwoCommitsPort getMetricValuesOfTwoCommitsPort;

  public GetMetricValuesOfTwoCommitsService(
      GetMetricValuesOfTwoCommitsPort getMetricValuesOfTwoCommitsPort) {
    this.getMetricValuesOfTwoCommitsPort = getMetricValuesOfTwoCommitsPort;
  }

  @Override
  public DeltaTree get(GetMetricsForTwoCommitsCommand command, Long projectId) {
    return getMetricValuesOfTwoCommitsPort.get(command, projectId);
  }
}
