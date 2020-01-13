package io.reflectoring.coderadar.graph.analyzer.adapter;

import io.reflectoring.coderadar.analyzer.port.driven.GetAnalyzingStatusPort;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAnalyzingStatusAdapter implements GetAnalyzingStatusPort {
  private final ProjectRepository projectRepository;

  public GetAnalyzingStatusAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public boolean getStatus(Long projectId) {
    return projectRepository.getProjectAnalyzingStatus(projectId);
  }
}
