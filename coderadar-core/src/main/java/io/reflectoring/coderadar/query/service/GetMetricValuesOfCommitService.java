package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import io.reflectoring.coderadar.query.port.driver.commitmetrics.GetMetricValuesOfCommitCommand;
import io.reflectoring.coderadar.query.port.driver.commitmetrics.GetMetricValuesOfCommitUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetMetricValuesOfCommitService implements GetMetricValuesOfCommitUseCase {
  private final GetMetricValuesOfCommitPort getMetricValuesOfCommitPort;

  private final GetProjectPort getProjectPort;

  public GetMetricValuesOfCommitService(
      GetMetricValuesOfCommitPort getMetricValuesOfCommitPort, GetProjectPort getProjectPort) {
    this.getMetricValuesOfCommitPort = getMetricValuesOfCommitPort;
    this.getProjectPort = getProjectPort;
  }

  @Override
  public List<MetricValueForCommit> get(long projectId, GetMetricValuesOfCommitCommand command) {
    if (getProjectPort.existsById(projectId)) {
      return getMetricValuesOfCommitPort.get(projectId, command);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
