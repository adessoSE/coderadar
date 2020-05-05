package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.port.driver.teams.create.CreateTeamForProjectCommand;

public interface CreateTeamForProjectPort {

    /**
     * Creates a team in the given project.
     * @param projectId The id of the project.
     * @param command The command containing the needed data.
     * @return The id of the newly created team.
     */
    Long createTeamForProject(long projectId, CreateTeamForProjectCommand command);
}
