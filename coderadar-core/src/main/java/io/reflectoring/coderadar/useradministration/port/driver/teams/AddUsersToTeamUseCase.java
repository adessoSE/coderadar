package io.reflectoring.coderadar.useradministration.port.driver.teams;

import java.util.List;

public interface AddUsersToTeamUseCase {

  /**
   * Adds users to a team.
   *
   * @param teamId The id of the team.
   * @param userIds The ids of the users.
   */
  void addUsersToTeam(long teamId, List<Long> userIds);
}
