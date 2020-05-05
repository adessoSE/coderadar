package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamCommand;

public interface CreateTeamPort {
  /**
   * Creates a new team.
   *
   * @param createTeamCommand The command containing the required data.
   * @return The id of the new team.
   */
  Long createTeam(CreateTeamCommand createTeamCommand);
}
