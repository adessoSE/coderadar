package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Branch;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import java.util.List;

public interface SaveCommitPort {

  /**
   * This method should be used for the initial creation of Commits when saving a project. It maps
   * all of the domain objects to entities and saves them in the DB.
   *
   * @param commits The commit tree to save.
   * @param branches All of the branches in the project
   * @param projectId The id of the project.
   */
  void saveCommits(List<Commit> commits, List<Branch> branches, Long projectId);

  /**
   * Marks the commits with the given ids as analyzed.
   *
   * @param commitIds A list with the DB ids of the commits.
   */
  void setCommitsWithIDsAsAnalyzed(List<Long> commitIds);
}
