package io.reflectoring.coderadar.core.analyzer.port.driven;

import io.reflectoring.coderadar.core.projectadministration.domain.Commit;

public interface SaveCommitPort {
  void saveCommit(Commit commit);
}
