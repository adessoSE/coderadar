package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import io.reflectoring.coderadar.query.port.driver.commitmetrics.GetMetricValuesOfCommitCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class GetMetricValuesOfCommitAdapter implements GetMetricValuesOfCommitPort {

  private final MetricQueryRepository metricQueryRepository;

  public GetMetricValuesOfCommitAdapter(MetricQueryRepository metricQueryRepository) {
    this.metricQueryRepository = metricQueryRepository;
  }

  @Override
  public List<MetricValueForCommit> get(long projectId, GetMetricValuesOfCommitCommand command) {
    List<Map<String, Object>> result =
        metricQueryRepository.getMetricValuesForCommit(
            projectId, command.getCommit(), command.getMetrics());
    List<MetricValueForCommit> values = new ArrayList<>();
    for (Map<String, Object> queryResult : result) {
      values.add(
          new MetricValueForCommit(
              (String) queryResult.get("name"), (long) queryResult.get("value")));
    }
    return values;
  }
}
