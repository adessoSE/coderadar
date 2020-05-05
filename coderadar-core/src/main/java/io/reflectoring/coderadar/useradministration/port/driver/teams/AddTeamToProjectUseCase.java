package io.reflectoring.coderadar.useradministration.port.driver.teams;

public interface AddTeamToProjectUseCase {

  /**
   * Creates a team in the given project.
   *
   * @param projectId The id of the project.
   * @param teamId The id of the team.
   */
  void addTeamToProject(long projectId, long teamId);
}
