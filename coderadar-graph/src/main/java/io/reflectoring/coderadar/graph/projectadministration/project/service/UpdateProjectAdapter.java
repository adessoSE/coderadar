package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.UpdateProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProjectAdapter implements UpdateProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final UpdateProjectRepository updateProjectRepository;

  @Autowired
  public UpdateProjectAdapter(
      GetProjectRepository getProjectRepository, UpdateProjectRepository updateProjectRepository) {
    this.getProjectRepository = getProjectRepository;
    this.updateProjectRepository = updateProjectRepository;
  }

  @Override
  public void update(Project project) {
    ProjectEntity projectEntity =
        getProjectRepository
            .findById(project.getId())
            .orElseThrow(() -> new ProjectNotFoundException(project.getId()));

    projectEntity.setName(project.getName());
    projectEntity.setVcsEnd(project.getVcsEnd());
    projectEntity.setVcsStart(project.getVcsStart());
    projectEntity.setVcsOnline(project.isVcsOnline());
    projectEntity.setVcsUsername(project.getVcsUsername());
    projectEntity.setVcsPassword(project.getVcsPassword());
    projectEntity.setVcsUrl(project.getVcsUrl());
    projectEntity.setWorkdirName(project.getWorkdirName());

    updateProjectRepository.save(projectEntity);
  }
}
