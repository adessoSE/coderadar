package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Commit;

public interface GetProjectHeadCommitPort {

  /**
   * @param projectId The id of the project.
   * @param branch The name of the branch
   * @return The HEAD commit of the branch.
   */
  Commit getHeadCommit(Long projectId, String branch);
}
