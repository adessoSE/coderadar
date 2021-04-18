package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.domain.Branch;
import io.reflectoring.coderadar.domain.Commit;
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
  void saveCommits(List<Commit> commits, List<Branch> branches, long projectId);

  /**
   * Marks the commit with the given id as analyzed.
   *
   * @param commitId The commit id.
   */
  void setCommitToAnalyzed(long commitId);
}
