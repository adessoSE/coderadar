package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.CreateProjectPort;
import io.reflectoring.coderadar.graph.exception.InvalidArgumentException;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectService implements CreateProjectPort {
  private final CreateProjectRepository createProjectRepository;

  @Autowired
  public CreateProjectService(CreateProjectRepository createProjectRepository) {
    this.createProjectRepository = createProjectRepository;
  }

  @Override
  public Long createProject(Project project) {
    return createProjectRepository.save(project).getId();
  }
}
