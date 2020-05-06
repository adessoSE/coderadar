package io.reflectoring.coderadar.useradministration.service.permissions;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.RemoveUserFromProjectPort;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.RemoveUserFromProjectUseCase;
import org.springframework.stereotype.Service;

@Service
public class RemoveUserFromProjectService implements RemoveUserFromProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final GetUserPort getUserPort;
  private final RemoveUserFromProjectPort removeUserFromProjectPort;

  public RemoveUserFromProjectService(
      GetProjectPort getProjectPort,
      GetUserPort getUserPort,
      RemoveUserFromProjectPort removeUserFromProjectPort) {
    this.getProjectPort = getProjectPort;
    this.getUserPort = getUserPort;
    this.removeUserFromProjectPort = removeUserFromProjectPort;
  }

  @Override
  public void removeUserFromProject(long projectId, long userId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    if (!getUserPort.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    removeUserFromProjectPort.removeUserFromProject(projectId, userId);
  }
}
