package io.reflectoring.coderadar.projectadministration.port.driven.analyzer;

import io.reflectoring.coderadar.projectadministration.domain.Commit;

import java.util.List;

public interface SaveCommitPort {
  void saveCommits(List<Commit> commits, Long id);

  void setCommitsWithIDsAsAnalyzed(List<Long> commitIds);
}
