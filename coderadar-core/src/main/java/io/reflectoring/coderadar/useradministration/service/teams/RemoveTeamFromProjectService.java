package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.RemoveTeamFromProjectPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.RemoveTeamFromProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class RemoveTeamFromProjectService implements RemoveTeamFromProjectUseCase {

  private final GetTeamPort getTeamPort;
  private final RemoveTeamFromProjectPort removeTeamFromProjectPort;

  public RemoveTeamFromProjectService(
      GetTeamPort getTeamPort, RemoveTeamFromProjectPort removeTeamFromProjectPort) {
    this.getTeamPort = getTeamPort;
    this.removeTeamFromProjectPort = removeTeamFromProjectPort;
  }

  @Override
  public void removeTeam(long projectId, long teamId) {
    if (getTeamPort.existsById(teamId)) {
      removeTeamFromProjectPort.removeTeam(projectId, teamId);
    } else {
      throw new TeamNotFoundException(teamId);
    }
  }
}
