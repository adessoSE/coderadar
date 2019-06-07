package io.reflectoring.coderadar.graph.projectadministration.project.service;

import io.reflectoring.coderadar.graph.projectadministration.project.repository.CreateProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectAdapter implements CreateProjectPort {
  private final CreateProjectRepository createProjectRepository;

  @Autowired
  public CreateProjectAdapter(CreateProjectRepository createProjectRepository) {
    this.createProjectRepository = createProjectRepository;
  }

  @Override
  public Long createProject(Project project) {
    return createProjectRepository.save(project).getId();
  }
}
