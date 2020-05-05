package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteTeamUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteTeamService implements DeleteTeamUseCase {

  private final GetTeamPort getTeamPort;
  private final DeleteTeamPort deleteTeamPort;

  public DeleteTeamService(GetTeamPort getTeamPort, DeleteTeamPort deleteTeamPort) {
    this.getTeamPort = getTeamPort;
    this.deleteTeamPort = deleteTeamPort;
  }

  @Override
  public void deleteTeam(long teamId) {
    if (getTeamPort.existsById(teamId)) {
      deleteTeamPort.deleteTeam(teamId);
    } else {
      throw new TeamNotFoundException(teamId);
    }
  }
}
