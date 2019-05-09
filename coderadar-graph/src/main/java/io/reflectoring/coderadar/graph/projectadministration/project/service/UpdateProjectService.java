package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.graph.exception.ProjectNotFoundException;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.GetProjectRepository;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.UpdateProjectRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UpdateProjectServiceNeo4j")
public class UpdateProjectService implements UpdateProjectPort {
  private final GetProjectRepository getProjectRepository;
  private final UpdateProjectRepository updateProjectRepository;

  @Autowired
  public UpdateProjectService(
      GetProjectRepository getProjectRepository, UpdateProjectRepository updateProjectRepository) {
    this.getProjectRepository = getProjectRepository;
    this.updateProjectRepository = updateProjectRepository;
  }

  @Override
  public void update(Project project) {
    Optional<Project> oldProject = getProjectRepository.findById(project.getId());

    if (oldProject.isPresent()) {
      updateProjectRepository.save(project);
    } else {
      throw new ProjectNotFoundException(
          String.format("There is no project with the ID %d. Updating failed.", project.getId()));
    }
  }
}
