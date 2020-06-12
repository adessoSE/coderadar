package io.reflectoring.coderadar.useradministration.port.driven;

import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.List;

public interface ListProjectsForTeamPort {
  /**
   * @param teamId The id of the team.
   * @return All projects the team has access to.
   */
  List<Project> listProjects(long teamId);
}
