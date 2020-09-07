package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectStatusAdapter implements ProjectStatusPort {
  private final ProjectRepository projectRepository;

  @Override
  public boolean isBeingProcessed(long projectId) {
    return projectRepository.isBeingProcessed(projectId);
  }

  @Override
  public void setBeingProcessed(long projectId, boolean isBeingProcessed) {
    projectRepository.setBeingProcessed(projectId, isBeingProcessed);
  }
}
