package io.reflectoring.coderadar.useradministration.service.teams;

import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.AddUsersToTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.AddUsersToTeamUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AddUsersToTeamService implements AddUsersToTeamUseCase {

  private final GetTeamPort getTeamPort;
  private final GetUserPort getUserPort;
  private final AddUsersToTeamPort addUsersToTeamPort;

  public AddUsersToTeamService(
      GetTeamPort getTeamPort, GetUserPort getUserPort, AddUsersToTeamPort addUsersToTeamPort) {
    this.getTeamPort = getTeamPort;
    this.getUserPort = getUserPort;
    this.addUsersToTeamPort = addUsersToTeamPort;
  }

  @Override
  public void addUsersToTeam(long teamId, List<Long> userIds) {
    if (!getTeamPort.existsById(teamId)) {
      throw new TeamNotFoundException(teamId);
    }
    for (Long userId : userIds) {
      if (!getUserPort.existsById(userId)) {
        throw new UserNotFoundException(userId);
      }
    }
    addUsersToTeamPort.addUsersToTeam(teamId, userIds);
  }
}
