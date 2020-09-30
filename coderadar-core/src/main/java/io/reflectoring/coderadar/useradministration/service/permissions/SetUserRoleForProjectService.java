package io.reflectoring.coderadar.useradministration.service.permissions;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.domain.ProjectRole;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.SetUserRoleForProjectPort;
import io.reflectoring.coderadar.useradministration.port.driver.permissions.SetUserRoleForProjectUseCase;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SetUserRoleForProjectService implements SetUserRoleForProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final GetUserPort getUserPort;
  private final SetUserRoleForProjectPort setUserRoleForProjectPort;

  private static final Logger logger = LoggerFactory.getLogger(SetUserRoleForProjectService.class);

  @Override
  public void setRole(long projectId, long userId, ProjectRole role) {
    if (!getProjectPort.existsById(projectId)) {
      throw new ProjectNotFoundException(projectId);
    }
    if (!getUserPort.existsById(userId)) {
      throw new UserNotFoundException(userId);
    }
    setUserRoleForProjectPort.setRole(projectId, userId, role, false);
    logger.info(
        "Assigned user with id: {} to project with id: {}, with the role: {}",
        userId,
        projectId,
        role.getValue());
  }
}
