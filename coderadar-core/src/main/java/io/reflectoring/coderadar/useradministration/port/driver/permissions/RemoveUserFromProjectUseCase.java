package io.reflectoring.coderadar.useradministration.port.driver.permissions;

public interface RemoveUserFromProjectUseCase {

  /**
   * Removes the role a given user has to a project. If the user is not in any team with access to
   * the project, they will no longer have access to the project. If the user is not assigned to the
   * project, nothing is changed.
   *
   * @param projectId The id of the project.
   * @param userId The id of the user.
   */
  void removeUserFromProject(long projectId, long userId);
}
