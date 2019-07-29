package io.reflectoring.coderadar.query.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricValuesOfCommitUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    return getMetricValuesOfCommitPort.get(commit.getName());
  }

  @Override
  public List<MetricValue> get(String commitHash) {
    return getMetricValuesOfCommitPort.get(commitHash);
  }
}
