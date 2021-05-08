package io.reflectoring.coderadar.useradministration.port.driver.get;

import io.reflectoring.coderadar.domain.ProjectWithRoles;
import java.util.List;

public interface ListProjectsForUserUseCase {

  /**
   * @param userId The id of the user.
   * @return All projects this user has access to.
   */
  List<ProjectWithRoles> listProjects(long userId);
}
