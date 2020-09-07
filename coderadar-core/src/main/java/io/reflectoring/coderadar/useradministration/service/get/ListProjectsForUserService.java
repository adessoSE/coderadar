package io.reflectoring.coderadar.useradministration.service.get;

import io.reflectoring.coderadar.projectadministration.domain.ProjectWithRoles;
import io.reflectoring.coderadar.useradministration.UserNotFoundException;
import io.reflectoring.coderadar.useradministration.port.driven.GetUserPort;
import io.reflectoring.coderadar.useradministration.port.driven.ListProjectsForUserPort;
import io.reflectoring.coderadar.useradministration.port.driver.get.ListProjectsForUserUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListProjectsForUserService implements ListProjectsForUserUseCase {

  private final ListProjectsForUserPort listProjectsForUserPort;
  private final GetUserPort getUserPort;

  @Override
  public List<ProjectWithRoles> listProjects(long userId) {
    if (this.getUserPort.existsById(userId)) {
      return listProjectsForUserPort.listProjects(userId);
    } else {
      throw new UserNotFoundException(userId);
    }
  }
}
