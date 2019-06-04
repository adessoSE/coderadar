package io.reflectoring.coderadar.core.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.core.projectadministration.domain.Commit;

public interface SaveCommitPort {
  void saveCommit(Commit commit);
}
