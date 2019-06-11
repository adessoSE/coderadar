package io.reflectoring.coderadar.analyzer.port.driver;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Project;

public interface AnalyzeCommitUseCase {
  void analyzeCommit(Commit commit, Project project);
}
