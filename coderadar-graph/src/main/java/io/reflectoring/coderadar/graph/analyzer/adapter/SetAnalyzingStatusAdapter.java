package io.reflectoring.coderadar.graph.analyzer.adapter;

import io.reflectoring.coderadar.analyzer.port.driven.SetAnalyzingStatusPort;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class SetAnalyzingStatusAdapter implements SetAnalyzingStatusPort {
  private final ProjectRepository projectRepository;

  public SetAnalyzingStatusAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void setStatus(Long projectId, boolean status) {
    projectRepository.setAnalyzingStatus(projectId, status);
  }
}
