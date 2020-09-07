package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.domain.ProjectWithRoles;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListProjectsForTeamUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListProjectsForTeamService implements ListProjectsForTeamUseCase {

  private final GetTeamPort getTeamPort;
  private final ListProjectsForTeamPort listProjectsForTeamPort;
  private final GetUserPort getUserPort;

  @Override
  public List<Project> listProjects(long teamId) {
    if (getTeamPort.existsById(teamId)) {
      return listProjectsForTeamPort.listProjects(teamId);
    } else {
      throw new TeamNotFoundException(teamId);
    }
  }

  @Override
  public List<ProjectWithRoles> listProjectsWithUserRoles(long teamId, long userId) {
    if (getTeamPort.existsById(teamId)) {
      if (getUserPort.existsById(userId)) {
        return listProjectsForTeamPort.listProjectsWithRolesForUser(teamId, userId);
      } else {
        throw new UserNotFoundException(userId);
      }
    } else {
      throw new TeamNotFoundException(teamId);
    }
  }
}
