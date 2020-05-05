package io.reflectoring.coderadar.useradministration.port.driven;

import java.util.List;

public interface DeleteUsersFromTeamPort {

  /**
   * Deletes users from a team. Any permissions users had for a project because of the team will be
   * lost after this operation.
   *
   * @param teamId The id of the team.
   * @param userIds The ids of the users.
   */
  void deleteUsersFromTeam(long teamId, List<Long> userIds);
}
