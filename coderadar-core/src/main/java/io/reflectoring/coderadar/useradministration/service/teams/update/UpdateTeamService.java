package io.reflectoring.coderadar.useradministration.service.teams.update;

import io.reflectoring.coderadar.useradministration.TeamAlreadyExistsException;
import io.reflectoring.coderadar.useradministration.TeamNotFoundException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.UpdateTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.update.UpdateTeamCommand;
import io.reflectoring.coderadar.useradministration.port.driver.teams.update.UpdateTeamUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTeamService implements UpdateTeamUseCase {

  private final UpdateTeamPort updateTeamPort;
  private final GetTeamPort getTeamPort;
  private final GetUserPort getUserPort;

  private static final Logger logger = LoggerFactory.getLogger(UpdateTeamService.class);

  @Override
  public void updateTeam(long teamId, UpdateTeamCommand updateTeamCommand) {
    if (!getTeamPort.existsById(teamId)) {
      throw new TeamNotFoundException(teamId);
    } else if (getTeamPort.existsByName(updateTeamCommand.getName())
        && getTeamPort.getByName(updateTeamCommand.getName()).getId() != teamId) {
      throw new TeamAlreadyExistsException(updateTeamCommand.getName());
    } else {
      if (updateTeamCommand.getUserIds() != null) {
        for (Long userId : updateTeamCommand.getUserIds()) {
          if (!getUserPort.existsById(userId)) {
            throw new UserNotFoundException(userId);
          }
        }
      }
      updateTeamPort.updateTeam(teamId, updateTeamCommand);
      logger.info(
          String.format("Updated team %s with id: %d", updateTeamCommand.getName(), teamId));
    }
  }
}
