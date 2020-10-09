package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.port.driver.teams.update.UpdateTeamCommand;

public interface UpdateTeamPort {

  /**
   * Updates an existing team.
   *
   * @param teamId The id of the team
   * @param updateTeamCommand The command containing the required data.
   */
  void updateTeam(long teamId, UpdateTeamCommand updateTeamCommand);
}
