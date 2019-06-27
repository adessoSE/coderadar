package io.reflectoring.coderadar.graph.query.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.query.repository.GetAvailableMetricsInProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetAvailableMetricsInProjectAdapter implements GetAvailableMetricsInProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository;

  @Autowired
  public GetAvailableMetricsInProjectAdapter(
      GetProjectRepository getProjectRepository,
      GetAvailableMetricsInProjectRepository getAvailableMetricsInProjectRepository) {
    this.getProjectRepository = getProjectRepository;
    this.getAvailableMetricsInProjectRepository = getAvailableMetricsInProjectRepository;
  }

  @Override
  public List<String> get(Long projectId) {
    Optional<ProjectEntity> persistedProject = getProjectRepository.findById(projectId);

    if (persistedProject.isPresent()) {
      return getAvailableMetricsInProjectRepository.getAvailableMetricsInProject(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
