package io.reflectoring.coderadar.useradministration.port.driver.teams;

public interface RemoveTeamFromProjectUseCase {

    /**
     * Removes a team from a project.
     * Any permissions users had for a project because of the team
     * will be lost after this operation.
     * @param teamId The id of the team.
     */
    void removeTeam(long teamId);
}
