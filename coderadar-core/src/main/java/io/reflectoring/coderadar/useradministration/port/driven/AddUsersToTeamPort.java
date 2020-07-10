package io.reflectoring.coderadar.useradministration.port.driven;

import java.util.List;

public interface AddUsersToTeamPort {

  /**
   * Adds users to a team. If a user is already in the team nothing is changed.
   *
   * @param teamId The id of the team.
   * @param userIds The ids of the users.
   */
  void addUsersToTeam(long teamId, List<Long> userIds);
}
