package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.analyzer.domain.MetricValueDTO;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service("GetMetricValuesOfCommitServiceNeo4j")
public class GetMetricValuesOfCommitService implements GetMetricValuesOfCommitPort {

  // TODO
  @Override
  public List<MetricValueDTO> get(String commitHash) {
    return new LinkedList<>();
  }
}
