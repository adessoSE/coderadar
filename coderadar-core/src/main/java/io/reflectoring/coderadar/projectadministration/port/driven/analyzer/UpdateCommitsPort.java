package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import java.util.List;

public interface UpdateCommitsPort {
  /**
   * Adds new commits to an existing project or removes commits from the project (e.g. after hard
   * reset and force push).
   *
   * @param projectId The project id
   * @param commits The complete commit tree for the project.
   * @param updatedBranches A list of branches that are new or have been changed.
   */
  void updateCommits(long projectId, List<Commit> commits, List<Branch> updatedBranches);
}
