package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Commit;
import java.util.List;

public interface SaveCommitPort {

  /**
   * Saves commits in the DB.
   *
   * @param commits The commits to save.
   * @param projectId The id of the project.
   */
  void saveCommits(List<Commit> commits, Long projectId);

  /**
   * Marks the commits with the given ids as analyzed.
   *
   * @param commitIds A list with the DB ids of the commits.
   */
  void setCommitsWithIDsAsAnalyzed(List<Long> commitIds);
}
