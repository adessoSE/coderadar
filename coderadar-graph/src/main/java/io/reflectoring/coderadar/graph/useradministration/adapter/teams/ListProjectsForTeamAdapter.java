package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForTeamPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListProjectsForTeamAdapter implements ListProjectsForTeamPort {

  private final ProjectMapper projectMapper = new ProjectMapper();
  private final ProjectRepository projectRepository;

  public ListProjectsForTeamAdapter(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public List<Project> listProjects(long teamId) {
    return projectMapper.mapNodeEntities(projectRepository.listProjectsByTeamId(teamId));
  }
}
