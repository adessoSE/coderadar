package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListProjectsForTeamUseCase;
import java.util.List;

public class ListProjectsForTeamService implements ListProjectsForTeamUseCase {

  private final GetTeamPort getTeamPort;
  private final ListProjectsForTeamPort listProjectsForTeamPort;

  public ListProjectsForTeamService(
      GetTeamPort getTeamPort, ListProjectsForTeamPort listProjectsForTeamPort) {
    this.getTeamPort = getTeamPort;
    this.listProjectsForTeamPort = listProjectsForTeamPort;
  }

  @Override
  public List<Project> listProjects(long teamId) {
    if (getTeamPort.existsById(teamId)) {
      return listProjectsForTeamPort.listProjects(teamId);
    } else {
      throw new TeamNotFoundException(teamId);
    }
  }
}
