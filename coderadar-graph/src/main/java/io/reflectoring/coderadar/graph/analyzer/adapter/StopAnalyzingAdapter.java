package io.reflectoring.coderadar.graph.analyzer.adapter;

import io.reflectoring.coderadar.analyzer.port.driven.StopAnalyzingPort;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class StopAnalyzingAdapter implements StopAnalyzingPort {
  private final ProjectRepository projectRepository;

  public StopAnalyzingAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void stop(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    projectRepository.setAnalyzingStatus(projectId, false);
  }
}
