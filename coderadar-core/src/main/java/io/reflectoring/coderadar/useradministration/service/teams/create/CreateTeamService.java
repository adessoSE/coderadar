package io.reflectoring.coderadar.useradministration.service.teams.create;

import io.reflectoring.coderadar.useradministration.TeamAlreadyExistsException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.CreateTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamCommand;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateTeamService implements CreateTeamUseCase {
  private final CreateTeamPort createTeamPort;
  private final GetTeamPort getTeamPort;
  private final GetUserPort getUserPort;

  private static final Logger logger = LoggerFactory.getLogger(CreateTeamService.class);

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
      long teamId = createTeamPort.createTeam(createTeamCommand);
      logger.info("Created team {} with id: {}", createTeamCommand.getName(), teamId);
      return teamId;
    }
  }
}
