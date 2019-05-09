package io.reflectoring.coderadar.core.query.service;

import io.reflectoring.coderadar.core.analyzer.domain.GroupedMetricValueDTO;
import io.reflectoring.coderadar.core.query.port.driven.GetMetricsForAllFilesInCommitPort;
import io.reflectoring.coderadar.core.query.port.driver.GetMetricsForAllFilesInCommitCommand;
import io.reflectoring.coderadar.core.query.port.driver.GetMetricsForAllFilesInCommitUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("GetMetricsForAllFilesInCommitService")
public class GetMetricsForAllFilesInCommitService implements GetMetricsForAllFilesInCommitUseCase {
  private final GetMetricsForAllFilesInCommitPort getMetricsForAllFilesInCommitPort;

  @Autowired
  public GetMetricsForAllFilesInCommitService(
      GetMetricsForAllFilesInCommitPort getMetricsForAllFilesInCommitPort) {
    this.getMetricsForAllFilesInCommitPort = getMetricsForAllFilesInCommitPort;
  }

  @Override
  public List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command) {
    return getMetricsForAllFilesInCommitPort.get(command);
  }
}
