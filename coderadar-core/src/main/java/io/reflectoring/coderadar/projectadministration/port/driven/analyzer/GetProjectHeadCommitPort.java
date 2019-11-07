package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Commit;

public interface GetProjectHeadCommitPort {
  Commit getHeadCommit(Long projectId);
}
