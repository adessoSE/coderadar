package io.reflectoring.coderadar.useradministration.port.driven;

public interface RemoveUserFromProjectPort {

  /**
   * Removes the role a given user has to a project. If the user is not in any team with access to
   * the project, they will no longer have access to the project.
   *
   * @param projectId The id of the project.
   * @param userId The id of the user.
   */
  void removeUserFromProject(long projectId, long userId);
}
