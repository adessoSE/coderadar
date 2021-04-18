package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.domain.ProjectRole;

public interface GetUserRoleForProjectPort {

  /**
   * Gives a user a role in the project.
   *
   * @param projectId The id of the project.
   * @param userId The id of the user.
   */
  ProjectRole getRole(long projectId, long userId);
}
