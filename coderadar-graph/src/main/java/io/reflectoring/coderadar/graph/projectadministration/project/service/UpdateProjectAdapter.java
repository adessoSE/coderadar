package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.UpdateProjectRepository;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    Optional<ProjectEntity> oldProject = getProjectRepository.findById(project.getId());

    if (oldProject.isPresent()) {
      oldProject.get().setName(project.getName());
      oldProject.get().setVcsEnd(project.getVcsEnd());
      oldProject.get().setVcsStart(project.getVcsStart());
      oldProject.get().setVcsOnline(project.isVcsOnline());
      oldProject.get().setVcsUsername(project.getVcsUsername());
      oldProject.get().setVcsPassword(project.getVcsPassword());
      oldProject.get().setVcsUrl(project.getVcsUrl());
      oldProject.get().setWorkdirName(project.getWorkdirName());

      updateProjectRepository.save(oldProject.get());
    } else {
      throw new ProjectNotFoundException(
          String.format("There is no project with the ID %d. Updating failed.", project.getId()));
    }
  }
}
