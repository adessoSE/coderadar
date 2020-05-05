package io.reflectoring.coderadar.useradministration.port.driven;

public interface RemoveTeamFromProjectPort {

    /**
     * Deletes a team.
     * Any permissions users had for a project because of the team
     * will be lost after this operation.
     * @param teamId The id of the team.
     */
    void deleteTeam(long teamId);
}
