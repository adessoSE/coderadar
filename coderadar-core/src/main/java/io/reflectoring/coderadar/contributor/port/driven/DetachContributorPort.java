package io.reflectoring.coderadar.contributor.port.driven;

import io.reflectoring.coderadar.contributor.domain.Contributor;
import java.util.List;

public interface DetachContributorPort {

  /**
   * Deletes the "works on" relationship between the contributor and the project
   *
   * @param contributor contributor to be detached from the project
   * @param projectId project the contributor is to be detached from
   */
  void detachContributorFromProject(Contributor contributor, long projectId);

  /**
   * Deletes the "works on" relationship between the contributors and the project
   *
   * @param contributors contributors to be detached from the project
   * @param projectId project the contributors are to be detached from
   */
  void detachContributorsFromProject(List<Contributor> contributors, long projectId);
}
