package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import java.util.List;

public interface AddCommitsPort {

  /**
   * Adds commit to an existing project.
   *
   * @param commits The commits to add.
   * @param projectId The id of the project.
   */
  void addCommits(List<Commit> commits, Long projectId);
}
