package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricValuesOfCommitUseCase;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetMetricValuesOfCommitService implements GetMetricValuesOfCommitUseCase {
  private final GetMetricValuesOfCommitPort getMetricValuesOfCommitPort;

  @Autowired
  public GetMetricValuesOfCommitService(GetMetricValuesOfCommitPort getMetricValuesOfCommitPort) {
    this.getMetricValuesOfCommitPort = getMetricValuesOfCommitPort;
  }

  @Override
  public List<MetricValue> get(Commit commit) {
    return new ArrayList<>();
  }

  @Override
  public List<MetricValueForCommit> get(GetMetricsForCommitCommand command) {
    return getMetricValuesOfCommitPort.get(command);
  }
}
