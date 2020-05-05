package io.reflectoring.coderadar.useradministration.service.permissions;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.DeleteUserRoleForProjectPort;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.DeleteUserRoleForProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserRoleForProjectService implements DeleteUserRoleForProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final GetUserPort getUserPort;
  private final DeleteUserRoleForProjectPort deleteUserRoleForProjectPort;

  public DeleteUserRoleForProjectService(
      GetProjectPort getProjectPort,
      GetUserPort getUserPort,
      DeleteUserRoleForProjectPort deleteUserRoleForProjectPort) {
    this.getProjectPort = getProjectPort;
    this.getUserPort = getUserPort;
    this.deleteUserRoleForProjectPort = deleteUserRoleForProjectPort;
  }

  @Override
  public void deleteRole(long projectId, long userId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    if (!getUserPort.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    deleteUserRoleForProjectPort.deleteRole(projectId, userId);
  }
}
