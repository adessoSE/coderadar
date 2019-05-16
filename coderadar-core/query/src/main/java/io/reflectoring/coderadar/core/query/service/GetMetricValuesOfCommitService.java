package io.reflectoring.coderadar.core.query.service;

import io.reflectoring.coderadar.core.analyzer.domain.Commit;
import io.reflectoring.coderadar.core.analyzer.domain.MetricValueDTO;
import io.reflectoring.coderadar.core.query.port.driven.GetMetricValuesOfCommitPort;
import io.reflectoring.coderadar.core.query.port.driver.GetMetricValuesOfCommitUseCase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("GetMetricValuesOfCommitService")
public class GetMetricValuesOfCommitService implements GetMetricValuesOfCommitUseCase {
  private final GetMetricValuesOfCommitPort getMetricValuesOfCommitPort;

  @Autowired
  public GetMetricValuesOfCommitService(
      @Qualifier("GetMetricValuesOfCommitServiceNeo4j")
          GetMetricValuesOfCommitPort getMetricValuesOfCommitPort) {
    this.getMetricValuesOfCommitPort = getMetricValuesOfCommitPort;
  }

  @Override
  public List<MetricValueDTO> get(Commit commit) {
    return getMetricValuesOfCommitPort.get(commit.getName());
  }

  @Override
  public List<MetricValueDTO> get(String commitHash) {
    return getMetricValuesOfCommitPort.get(commitHash);
  }
}
