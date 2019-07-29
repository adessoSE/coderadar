package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.MetricValueForCommitQueryResult;
import io.reflectoring.coderadar.graph.query.repository.GetMetricValuesOfCommitRepository;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GetMetricValuesOfCommitAdapter implements GetMetricValuesOfCommitPort {

  private final GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository;

  public GetMetricValuesOfCommitAdapter(GetMetricValuesOfCommitRepository getMetricValuesOfCommitRepository) {
    this.getMetricValuesOfCommitRepository = getMetricValuesOfCommitRepository;
  }

  @Override
  public List<MetricValueForCommit> get(GetMetricsForCommitCommand command) {
    List<MetricValueForCommitQueryResult> result = getMetricValuesOfCommitRepository.getMetricValuesForCommit(command.getCommit(), command.getMetrics());
    List<MetricValueForCommit> values = new ArrayList<>();
    for(MetricValueForCommitQueryResult queryResult : result){
      values.add(new MetricValueForCommit(queryResult.getName(), queryResult.getValue()));
    }
    return values;
  }
}
