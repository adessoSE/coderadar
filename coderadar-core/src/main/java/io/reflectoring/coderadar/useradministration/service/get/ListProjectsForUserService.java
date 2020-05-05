package io.reflectoring.coderadar.useradministration.service.get;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListProjectsForUserUseCase;
import java.util.List;

public class ListProjectsForUserService implements ListProjectsForUserUseCase {

  private final ListProjectsForUserPort listProjectsForUserPort;
  private final GetUserPort getUserPort;

  public ListProjectsForUserService(
      ListProjectsForUserPort listProjectsForUserPort, GetUserPort getUserPort) {
    this.listProjectsForUserPort = listProjectsForUserPort;
    this.getUserPort = getUserPort;
  }

  @Override
  public List<Project> listProjects(long userId) {
    if (this.getUserPort.existsById(userId)) {
      return listProjectsForUserPort.listProjects(userId);
    } else {
      throw new UserNotFoundException(userId);
    }
  }
}
