package io.reflectoring.coderadar.graph.projectadministration.project.adapter;

import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.CreateProjectPort;
import org.springframework.stereotype.Service;

@Service
public class CreateProjectAdapter implements CreateProjectPort {
  private final ProjectRepository projectRepository;

  private final ProjectMapper projectMapper = new ProjectMapper();

  public CreateProjectAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public long createProject(Project project) {
    return projectRepository.save(projectMapper.mapDomainObject(project)).getId();
  }
}
