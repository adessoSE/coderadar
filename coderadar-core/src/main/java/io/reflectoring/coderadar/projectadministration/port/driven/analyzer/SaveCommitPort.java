package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import java.util.Collection;

public interface SaveCommitPort {
  void saveCommit(Commit commit);

  void saveCommits(Collection<Commit> commits);
}
