package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAvailableMetricsInProjectAdapter implements GetAvailableMetricsInProjectPort {
  private final MetricQueryRepository metricQueryRepository;

  @Override
  public List<String> get(long projectId) {
    return metricQueryRepository.getAvailableMetricsInProject(projectId);
  }
}
