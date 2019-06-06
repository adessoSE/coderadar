package io.reflectoring.coderadar.analyzer.port.driver;

import io.reflectoring.coderadar.analyzer.domain.Commit;

public interface AnalyzeCommitUseCase {
  void analyzeCommit(Commit commit);
}
