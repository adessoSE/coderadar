package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
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
    return projectRepository
        .findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException(projectId))
        .isBeingProcessed();
  }

  @Override
  public void setBeingProcessed(Long projectId, boolean isBeingProcessed) {
    ProjectEntity projectEntity =
        projectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    projectEntity.setBeingProcessed(isBeingProcessed);
    projectRepository.save(projectEntity);
  }
}
