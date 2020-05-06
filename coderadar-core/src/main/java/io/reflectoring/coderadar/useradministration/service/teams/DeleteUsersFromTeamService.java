package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteUsersFromTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.DeleteUsersFromTeamUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DeleteUsersFromTeamService implements DeleteUsersFromTeamUseCase {

  private final GetTeamPort getTeamPort;
  private final GetUserPort getUserPort;
  private final DeleteUsersFromTeamPort deleteUsersFromTeamPort;

  public DeleteUsersFromTeamService(
      GetTeamPort getTeamPort,
      GetUserPort getUserPort,
      DeleteUsersFromTeamPort deleteUsersFromTeamPort) {
    this.getTeamPort = getTeamPort;
    this.getUserPort = getUserPort;
    this.deleteUsersFromTeamPort = deleteUsersFromTeamPort;
  }

  @Override
  public void deleteUsersFromTeam(long teamId, List<Long> userIds) {
    if (!getTeamPort.existsById(teamId)) {
      throw new TeamNotFoundException(teamId);
    }
    for (Long userId : userIds) {
      if (!getUserPort.existsById(userId)) {
        throw new UserNotFoundException(userId);
      }
    }
    deleteUsersFromTeamPort.deleteUsersFromTeam(teamId, userIds);
  }
}
