package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetAvailableMetricsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetAvailableMetricsInProjectAdapter implements GetAvailableMetricsInProjectPort {
  private final ProjectRepository projectRepository;
  private final GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository;

  public GetAvailableMetricsInProjectAdapter(
      ProjectRepository projectRepository,
      GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository) {
    this.projectRepository = projectRepository;
    this.getAvailableMetricsInProjectRepository = getAvailableMetricsInProjectRepository;
  }

  @Override
  public List<String> get(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    return getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(projectId);
  }
}
