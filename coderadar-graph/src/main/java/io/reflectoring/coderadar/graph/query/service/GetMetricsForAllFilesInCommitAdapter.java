package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.analyzer.domain.GroupedMetricValueDTO;
import io.reflectoring.coderadar.query.port.driven.GetMetricsForAllFilesInCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForAllFilesInCommitCommand;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetMetricsForAllFilesInCommitAdapter implements GetMetricsForAllFilesInCommitPort {

  // TODO
  @Override
  public List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command) {
    return new LinkedList<GroupedMetricValueDTO>();
  }
}
