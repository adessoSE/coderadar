package io.reflectoring.coderadar.useradministration.service.teams.create;

import io.reflectoring.coderadar.useradministration.TeamAlreadyExistsException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.CreateTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamCommand;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTeamService implements CreateTeamUseCase {
  private final CreateTeamPort createTeamPort;
  private final GetTeamPort getTeamPort;
  private final GetUserPort getUserPort;

  @Override
  public Long createTeam(CreateTeamCommand createTeamCommand) {
    if (getTeamPort.existsByName(createTeamCommand.getName())) {
      throw new TeamAlreadyExistsException(createTeamCommand.getName());
    } else {
      if (createTeamCommand.getUserIds() != null) {
        for (Long userId : createTeamCommand.getUserIds()) {
          if (!getUserPort.existsById(userId)) {
            throw new UserNotFoundException(userId);
          }
        }
      }
      return createTeamPort.createTeam(createTeamCommand);
    }
  }
}
