package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.ValidationUtils;
import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.query.domain.MetricValueForCommit;
import io.reflectoring.coderadar.query.port.driven.GetMetricValuesOfCommitPort;
import io.reflectoring.coderadar.query.port.driver.commitmetrics.GetMetricValuesOfCommitCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetMetricValuesOfCommitAdapter implements GetMetricValuesOfCommitPort {

  private final MetricQueryRepository metricQueryRepository;

  @Override
  public List<MetricValueForCommit> get(long projectId, GetMetricValuesOfCommitCommand command) {
    String commitHash = ValidationUtils.validateAndTrimCommitHash(command.getCommit());
    List<Map<String, Object>> result =
        metricQueryRepository.getMetricValuesForCommit(projectId, commitHash, command.getMetrics());
    List<MetricValueForCommit> values = new ArrayList<>(result.size());
    for (Map<String, Object> queryResult : result) {
      values.add(
          new MetricValueForCommit(
              (String) queryResult.get("name"), (long) queryResult.get("value")));
    }
    return values;
  }
}
