package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.domain.Commit;

import java.util.List;

public interface AddCommitsPort {

  /**
   * Adds new commits to an existing project,.
   *
   * @param projectId The project id
   * @param commits The complete commit tree for the project.
   * @param updatedBranches A list of branches that are new are have been changed.
   */
  void addCommits(long projectId, List<Commit> commits, List<Branch> updatedBranches);
}
