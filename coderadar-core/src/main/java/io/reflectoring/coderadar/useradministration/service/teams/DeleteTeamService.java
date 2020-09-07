package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteTeamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteTeamService implements DeleteTeamUseCase {

  private final GetTeamPort getTeamPort;
  private final DeleteTeamPort deleteTeamPort;

  @Override
  public void deleteTeam(long teamId) {
    if (getTeamPort.existsById(teamId)) {
      deleteTeamPort.deleteTeam(teamId);
    } else {
      throw new TeamNotFoundException(teamId);
    }
  }
}
