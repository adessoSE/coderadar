package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import io.reflectoring.coderadar.query.port.driver.GetMetricsForCommitCommand;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GetMetricValuesOfCommitAdapter implements GetMetricValuesOfCommitPort {

  private final MetricQueryRepository metricQueryRepository;

  public GetMetricValuesOfCommitAdapter(MetricQueryRepository metricQueryRepository) {
    this.metricQueryRepository = metricQueryRepository;
  }

  @Override
  public List<MetricValueForCommit> get(GetMetricsForCommitCommand command, long projectId) {
    List<HashMap<String, Object>> result =
        metricQueryRepository.getMetricValuesForCommit2(
            projectId, command.getCommit(), command.getMetrics());
    List<MetricValueForCommit> values = new ArrayList<>();
    for (HashMap<String, Object> queryResult : result) {
      values.add(new MetricValueForCommit((String)queryResult.get("name"), (long)queryResult.get("value")));
    }
    return values;
  }
}
