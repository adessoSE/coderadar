package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.repository.MetricQueryRepository;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAvailableMetricsInProjectAdapter implements GetAvailableMetricsInProjectPort {
  private final MetricQueryRepository metricQueryRepository;

  public GetAvailableMetricsInProjectAdapter(MetricQueryRepository metricQueryRepository) {
    this.metricQueryRepository = metricQueryRepository;
  }

  @Override
  public List<String> get(long projectId) {
    return metricQueryRepository.getAvailableMetricsInProject(projectId);
  }
}
