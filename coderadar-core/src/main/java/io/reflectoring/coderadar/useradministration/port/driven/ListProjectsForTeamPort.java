package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.domain.Project;
import io.reflectoring.coderadar.domain.ProjectWithRoles;
import java.util.List;

public interface ListProjectsForTeamPort {
  /**
   * @param teamId The id of the team.
   * @return All projects the team has access to.
   */
  List<Project> listProjects(long teamId);

  /**
   * @param teamId The id of the team.
   * @param userId The id of the user.
   * @return All projects the team has access to and the roles the user has for the project.
   */
  List<ProjectWithRoles> listProjectsWithRolesForUser(long teamId, long userId);
}
