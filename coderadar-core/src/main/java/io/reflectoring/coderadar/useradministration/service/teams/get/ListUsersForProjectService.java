package io.reflectoring.coderadar.useradministration.service.teams.get;

import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.useradministration.domain.User;
import io.reflectoring.coderadar.useradministration.port.driven.ListUsersForProjectPort;
import io.reflectoring.coderadar.useradministration.port.driver.teams.get.ListUsersForProjectUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ListUsersForProjectService implements ListUsersForProjectUseCase {

  private final GetProjectPort getProjectPort;
  private final ListUsersForProjectPort listUsersForProjectPort;

  public ListUsersForProjectService(
      GetProjectPort getProjectPort, ListUsersForProjectPort listUsersForProjectPort) {
    this.getProjectPort = getProjectPort;
    this.listUsersForProjectPort = listUsersForProjectPort;
  }

  @Override
  public List<User> listUsers(long projectId) {
    if (getProjectPort.existsById(projectId)) {
      return listUsersForProjectPort.listUsers(projectId);
    } else {
      throw new ProjectNotFoundException(projectId);
    }
  }
}
