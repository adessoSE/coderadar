package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;

public interface SetUserRoleForProjectPort {

  /**
   * Gives a user a role in the project.
   *
   * @param projectId The id of the project.
   * @param userId The id of the user.
   * @param role The role to set.
   */
  void setRole(long projectId, long userId, ProjectRole role);
}
