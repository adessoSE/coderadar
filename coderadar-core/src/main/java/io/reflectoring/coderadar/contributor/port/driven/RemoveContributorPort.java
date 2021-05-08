package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.domain.Contributor;
import java.util.List;

public interface RemoveContributorPort {

  /**
   * Removes the "works on" relationship between the contributor and the project
   *
   * @param contributor contributor to be removed from the project
   * @param projectId project the contributor is to be removed from
   */
  void removeContributorFromProject(Contributor contributor, long projectId);

  /**
   * Remove the the contributors from the project
   *
   * @param contributors contributors to be removed from the project
   * @param projectId project the contributors are to be removed from
   */
  void removeContributorsFromProject(List<Contributor> contributors, long projectId);
}
