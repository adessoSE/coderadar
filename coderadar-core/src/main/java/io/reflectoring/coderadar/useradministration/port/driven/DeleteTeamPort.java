package io.reflectoring.coderadar.useradministration.port.driven;

public interface DeleteTeamPort {
    /**
     * Deletes a team.
     * @param teamId The id of the team.
     */
    void deleteTeam(long teamId);
}
