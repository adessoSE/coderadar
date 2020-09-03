package io.reflectoring.coderadar.useradministration.port.driver.teams;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;

public interface AddTeamToProjectUseCase {

  /**
   * Creates a team in the given project. If the team is already assigned to the project, the
   * existing role is overwritten.
   *
   * @param projectId The id of the project.
   * @param teamId The id of the team.
   * @param role The role to add the team with.
   */
  void addTeamToProject(long projectId, long teamId, ProjectRole role);
}
