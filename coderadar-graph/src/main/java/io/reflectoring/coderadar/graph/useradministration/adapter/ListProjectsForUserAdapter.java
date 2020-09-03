package io.reflectoring.coderadar.graph.useradministration.adapter;

import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForUserPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListProjectsForUserAdapter implements ListProjectsForUserPort {

  private final ProjectRepository projectRepository;
  private final ProjectMapper projectMapper = new ProjectMapper();

  public ListProjectsForUserAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public List<Project> listProjects(long userId) {
    return projectMapper.mapNodeEntities(projectRepository.findProjectsByUserId(userId));
  }
}
