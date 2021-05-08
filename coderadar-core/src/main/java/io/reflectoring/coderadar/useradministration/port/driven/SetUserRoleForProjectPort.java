package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.domain.ProjectRole;

public interface SetUserRoleForProjectPort {

  /**
   * Gives a user a role in the project. If the user is already assigned to the project, the
   * existing role is overwritten.
   *
   * @param projectId The id of the project.
   * @param userId The id of the user.
   * @param role The role to set.
   * @param creator Set to true if the user the creator of this project.
   */
  void setRole(long projectId, long userId, ProjectRole role, boolean creator);
}
