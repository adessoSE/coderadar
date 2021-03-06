package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.analyzer.domain.MetricNameMapper;
import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAvailableMetricsInProjectAdapter implements GetAvailableMetricsInProjectPort {
  private final MetricQueryRepository metricQueryRepository;

  @Override
  public List<String> get(long projectId) {
    return metricQueryRepository.getAvailableMetricsInProject(projectId).stream()
        .map(MetricNameMapper::mapToString)
        .collect(Collectors.toList());
  }
}
