package io.reflectoring.coderadar.useradministration.port.driver.permissions;

public interface DeleteUserRoleForProjectUseCase {

    /**
     * Removes the role a given user has to a project.
     * If the user is not in any team with access to the project, they
     * will no longer have access to the project.
     * @param projectId The id of the project.
     * @param userId The id of the user.
     */
    void deleteRole(long projectId, long userId);
}
