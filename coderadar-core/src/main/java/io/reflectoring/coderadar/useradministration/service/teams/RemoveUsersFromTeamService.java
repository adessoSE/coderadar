package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.UserNotInTeamException;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.RemoveUsersFromTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.RemoveUsersFromTeamUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RemoveUsersFromTeamService implements RemoveUsersFromTeamUseCase {

  private final GetTeamPort getTeamPort;
  private final GetUserPort getUserPort;
  private final RemoveUsersFromTeamPort removeUsersFromTeamPort;

  public RemoveUsersFromTeamService(
      GetTeamPort getTeamPort,
      GetUserPort getUserPort,
      RemoveUsersFromTeamPort removeUsersFromTeamPort) {
    this.getTeamPort = getTeamPort;
    this.getUserPort = getUserPort;
    this.removeUsersFromTeamPort = removeUsersFromTeamPort;
  }

  @Override
  public void removeUsersFromTeam(long teamId, List<Long> userIds) {
    if (!getTeamPort.existsById(teamId)) {
      throw new TeamNotFoundException(teamId);
    }
    for (Long userId : userIds) {
      if (!getUserPort.existsById(userId)) {
        throw new UserNotFoundException(userId);
      }
      if (getTeamPort.get(teamId).getMembers().stream().noneMatch(user -> user.getId() == userId)) {
        throw new UserNotInTeamException(userId, teamId);
      }
    }
    removeUsersFromTeamPort.removeUsersFromTeam(teamId, userIds);
  }
}
