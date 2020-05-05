package io.reflectoring.coderadar.useradministration.port.driver.teams.create;

public interface CreateTeamUseCase {

    /**
     * Creates a new team.
     * @param createTeamCommand The command containing the required data.
     * @return The id of the new team.
     */
    Long createTeam(CreateTeamCommand createTeamCommand);
}
