package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.analyzer.domain.Commit;

public interface SaveCommitPort {
  void saveCommit(Commit commit);
}
