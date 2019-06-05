package io.reflectoring.coderadar.core.analyzer.port.driver;

import io.reflectoring.coderadar.core.projectadministration.domain.Commit;

public interface AnalyzeCommitUseCase {
  void analyzeCommit(Commit commit);
}
