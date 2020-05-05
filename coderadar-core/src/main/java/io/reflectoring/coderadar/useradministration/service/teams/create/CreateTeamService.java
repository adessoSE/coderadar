package io.reflectoring.coderadar.useradministration.service.teams.create;

import io.reflectoring.coderadar.useradministration.TeamAlreadyExistsException;
import io.reflectoring.coderadar.useradministration.port.driven.CreateTeamPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateTeamService implements CreateTeamPort {
  private final CreateTeamPort createTeamPort;
  private final GetTeamPort getTeamPort;

  public CreateTeamService(CreateTeamPort createTeamPort, GetTeamPort getTeamPort) {
    this.createTeamPort = createTeamPort;
    this.getTeamPort = getTeamPort;
  }

  @Override
  public Long createTeam(CreateTeamCommand createTeamCommand) {
    if (getTeamPort.existsByName(createTeamCommand.getName())) {
      throw new TeamAlreadyExistsException(createTeamCommand.getName());
    } else {
      return createTeamPort.createTeam(createTeamCommand);
    }
  }
}
