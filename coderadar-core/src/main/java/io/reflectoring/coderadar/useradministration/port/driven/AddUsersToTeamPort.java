package io.reflectoring.coderadar.useradministration.port.driven;

import java.util.List;

public interface AddUsersToTeamPort {

    /**
     * Adds users to a team.
     * @param teamId The id of the team.
     * @param userIds The ids of the users.
     */
    void addUsersToTeam(long teamId, List<Long> userIds);
}
