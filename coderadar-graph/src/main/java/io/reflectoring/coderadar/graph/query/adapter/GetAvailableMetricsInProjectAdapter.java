package io.reflectoring.coderadar.graph.query.adapter;

import io.reflectoring.coderadar.graph.query.repository.GetAvailableMetricsInProjectRepository;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAvailableMetricsInProjectAdapter implements GetAvailableMetricsInProjectPort {
  private final GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository;

  public GetAvailableMetricsInProjectAdapter(
      GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository) {
    this.getAvailableMetricsInProjectRepository = getAvailableMetricsInProjectRepository;
  }

  @Override
  public List<String> get(Long projectId) {
    return getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(projectId);
  }
}
