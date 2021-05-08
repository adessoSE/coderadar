package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.domain.ProjectWithRoles;
import java.util.List;

public interface ListProjectsForUserPort {

  /**
   * @param userId The id of the user.
   * @return All projects this user has access to.
   */
  List<ProjectWithRoles> listProjects(long userId);

  List<Project> listProjectsCreatedByUser(long userId);
}
