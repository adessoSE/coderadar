package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Commit;

public interface GetProjectHeadCommitPort {

  /**
   * @param projectId The id of the project.
   * @return The HEAD commit of the project.
   */
  Commit getHeadCommit(Long projectId);
}
