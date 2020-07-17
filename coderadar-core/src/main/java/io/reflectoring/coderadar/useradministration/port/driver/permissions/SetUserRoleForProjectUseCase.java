package io.reflectoring.coderadar.useradministration.port.driver.permissions;

import io.reflectoring.coderadar.useradministration.domain.ProjectRole;

public interface SetUserRoleForProjectUseCase {

  /**
   * Gives a user a role in the project. If the user is already assigned to the project, the
   * existing role is overwritten.
   *
   * @param projectId The id of the project.
   * @param userId The id of the user.
   * @param role The role to set.
   */
  void setRole(long projectId, long userId, ProjectRole role);
}
