package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetAvailableMetricsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import java.util.List;
import java.util.Optional;
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
    Optional<ProjectEntity> persistedProject = projectRepository.findProjectById(projectId);
    if (persistedProject.isPresent()) {
      return getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
