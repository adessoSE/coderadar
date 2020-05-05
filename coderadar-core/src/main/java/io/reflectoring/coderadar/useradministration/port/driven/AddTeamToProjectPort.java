package io.reflectoring.coderadar.useradministration.port.driven;

public interface AddTeamToProjectPort {

    /**
     * Creates a team in the given project.
     * @param projectId The id of the project.
     * @param teamId The team id.
     */
    void addTeamToProject(long projectId, long teamId);
}
