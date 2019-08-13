package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricValuesOfCommitUseCase;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetMetricValuesOfCommitService implements GetMetricValuesOfCommitUseCase {
  private final GetMetricValuesOfCommitPort getMetricValuesOfCommitPort;

  private final GetProjectPort getProjectPort;

  @Autowired
  public GetMetricValuesOfCommitService(
      GetMetricValuesOfCommitPort getMetricValuesOfCommitPort, GetProjectPort getProjectPort) {
    this.getMetricValuesOfCommitPort = getMetricValuesOfCommitPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<MetricValue> get(Commit commit) {
    return new ArrayList<>();
  }

  @Override
  public List<MetricValueForCommit> get(GetMetricsForCommitCommand command, Long projectId) {
    getProjectPort.get(projectId);
    return getMetricValuesOfCommitPort.get(command, projectId);
  }
}
