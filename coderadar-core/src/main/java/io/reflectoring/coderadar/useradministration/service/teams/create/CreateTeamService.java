package io.reflectoring.coderadar.useradministration.service.teams.create;

import io.reflectoring.coderadar.useradministration.port.driven.CreateTeamPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateTeamService implements CreateTeamPort {
  private final CreateTeamPort createTeamPort;

  public CreateTeamService(CreateTeamPort createTeamPort) {
    this.createTeamPort = createTeamPort;
  }

  @Override
  public Long createTeam(CreateTeamCommand createTeamCommand) {
    return createTeamPort.createTeam(createTeamCommand);
  }
}
