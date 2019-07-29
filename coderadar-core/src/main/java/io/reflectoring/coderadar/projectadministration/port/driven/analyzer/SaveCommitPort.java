package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import java.util.List;

public interface SaveCommitPort {
  void saveCommits(List<Commit> commits, Long id);

  void saveCommit(Commit commit);
}
