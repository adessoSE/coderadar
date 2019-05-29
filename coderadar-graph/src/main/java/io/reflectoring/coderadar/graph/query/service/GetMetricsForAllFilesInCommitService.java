package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.core.analyzer.domain.GroupedMetricValueDTO;
import io.reflectoring.coderadar.core.query.port.driven.GetMetricsForAllFilesInCommitPort;
import io.reflectoring.coderadar.core.query.port.driver.GetMetricsForAllFilesInCommitCommand;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service("GetMetricsForAllFilesInCommitServiceNeo4j")
public class GetMetricsForAllFilesInCommitService implements GetMetricsForAllFilesInCommitPort {

  // TODO
  @Override
  public List<GroupedMetricValueDTO> get(GetMetricsForAllFilesInCommitCommand command) {
    return new LinkedList<GroupedMetricValueDTO>();
  }
}
