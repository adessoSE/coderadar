package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectStatusAdapter implements ProjectStatusPort {

  private final GetProjectRepository getProjectRepository;

  @Autowired
  public ProjectStatusAdapter(GetProjectRepository getProjectRepository) {
    this.getProjectRepository = getProjectRepository;
  }

  @Override
  public boolean isBeingProcessed(Long projectId) {
    return getProjectRepository
        .findById(projectId)
        .orElseThrow(() -> new ProjectNotFoundException(projectId))
        .isBeingProcessed();
  }

  @Override
  public void setBeingProcessed(Long projectId, boolean isBeingProcessed) {
    ProjectEntity projectEntity =
        getProjectRepository
            .findById(projectId)
            .orElseThrow(() -> new ProjectNotFoundException(projectId));
    projectEntity.setBeingProcessed(isBeingProcessed);
    getProjectRepository.save(projectEntity);
  }
}
