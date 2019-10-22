package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectStatusAdapter implements ProjectStatusPort {

  private final ProjectRepository projectRepository;

  @Autowired
  public ProjectStatusAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public boolean isBeingProcessed(Long projectId) {
    return projectRepository.isBeingProcessed(projectId);
  }

  @Override
  public void setBeingProcessed(Long projectId, boolean isBeingProcessed) {
    projectRepository.setBeingProcessed(projectId, isBeingProcessed);
  }
}
