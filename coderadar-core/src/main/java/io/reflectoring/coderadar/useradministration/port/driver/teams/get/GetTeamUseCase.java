package io.reflectoring.coderadar.useradministration.port.driver.teams.get;

import io.reflectoring.coderadar.domain.Team;

public interface GetTeamUseCase {

  /**
   * @param teamId The team id.
   * @return The team with the given id.
   */
  Team get(long teamId);
}
