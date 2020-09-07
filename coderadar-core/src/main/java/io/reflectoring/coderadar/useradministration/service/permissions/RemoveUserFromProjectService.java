package io.reflectoring.coderadar.useradministration.service.permissions;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.UserNotAssignedException;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.ListUsersForProjectPort;
import io.reflectoring.coderadar.useradministration.port.driven.RemoveUserFromProjectPort;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.RemoveUserFromProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RemoveUserFromProjectService implements RemoveUserFromProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final GetUserPort getUserPort;
  private final RemoveUserFromProjectPort removeUserFromProjectPort;
  private final ListUsersForProjectPort listUsersForProjectPort;

  @Override
  public void removeUserFromProject(long projectId, long userId) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    if (!getUserPort.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    if (listUsersForProjectPort.listUsers(projectId).stream()
        .anyMatch(user -> user.getId() == userId)) {
      removeUserFromProjectPort.removeUserFromProject(projectId, userId);
    } else {
      throw new UserNotAssignedException(projectId, userId);
    }
  }
}
