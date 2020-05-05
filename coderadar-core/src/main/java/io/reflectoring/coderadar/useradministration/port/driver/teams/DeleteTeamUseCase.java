package io.reflectoring.coderadar.useradministration.port.driver.teams;

public interface DeleteTeamUseCase {

  /**
   * Deletes a team.
   *
   * @param teamId The id of the team.
   */
  void deleteTeam(long teamId);
}
