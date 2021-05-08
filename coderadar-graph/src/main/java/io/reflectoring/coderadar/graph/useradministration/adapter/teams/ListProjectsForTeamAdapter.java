package io.reflectoring.coderadar.graph.useradministration.adapter.teams;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.domain.ProjectRole;
import io.reflectoring.coderadar.domain.ProjectWithRoles;
import io.reflectoring.coderadar.graph.projectadministration.domain.ProjectEntity;
import io.reflectoring.coderadar.graph.projectadministration.project.ProjectMapper;
import io.reflectoring.coderadar.graph.projectadministration.project.repository.ProjectRepository;
import io.reflectoring.coderadar.graph.useradministration.repository.UserRepository;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForTeamPort;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListProjectsForTeamAdapter implements ListProjectsForTeamPort {

  private final ProjectRepository projectRepository;
  private final UserRepository userRepository;

  private final ProjectMapper projectMapper = new ProjectMapper();

  @Override
  public List<Project> listProjects(long teamId) {
    return projectMapper.mapNodeEntities(projectRepository.listProjectsByTeamId(teamId));
  }

  @Override
  public List<ProjectWithRoles> listProjectsWithRolesForUser(long teamId, long userId) {
    List<ProjectEntity> projectEntities = projectRepository.listProjectsByTeamId(teamId);
    List<ProjectWithRoles> result = new ArrayList<>(projectEntities.size());
    for (ProjectEntity project : projectEntities) {
      Set<String> roles =
          new HashSet<>(userRepository.getUserRolesForProjectInTeams(project.getId(), userId));
      roles.add(userRepository.getUserRoleForProject(project.getId(), userId));
      roles.removeIf(Objects::isNull);
      result.add(
          new ProjectWithRoles(
              projectMapper.mapGraphObject(project),
              roles.stream()
                  .map(s -> ProjectRole.valueOf(s.toUpperCase()))
                  .collect(Collectors.toList())));
    }
    return result;
  }
}
