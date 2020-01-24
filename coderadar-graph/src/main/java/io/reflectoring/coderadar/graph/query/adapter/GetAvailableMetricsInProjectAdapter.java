package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAvailableMetricsInProjectAdapter implements GetAvailableMetricsInProjectPort {
  private final MetricQueryRepository metricQueryRepository;

  public GetAvailableMetricsInProjectAdapter(MetricQueryRepository metricQueryRepository) {
    this.metricQueryRepository = metricQueryRepository;
  }

  @Override
  public List<String> get(Long projectId) {
    return metricQueryRepository.getAvailableMetricsInProject(projectId);
  }
}
