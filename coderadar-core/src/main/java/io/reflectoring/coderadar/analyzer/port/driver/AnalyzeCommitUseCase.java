package io.reflectoring.coderadar.analyzer.port.driver;

import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.analyzer.service.filepatterns.FilePatternMatcher;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import java.util.List;

public interface AnalyzeCommitUseCase {

  /**
   * Analyzes a single commit.
   *
   * @param commit The commit to analyze.
   * @param project The project the commit is part of.
   * @param analyzers The analyzers to use.
   * @param filePatterns The file patterns to use.
   * @return A list of metric values calculated for the commit.
   */
  List<MetricValue> analyzeCommit(
      Commit commit,
      Project project,
      List<SourceCodeFileAnalyzerPlugin> analyzers,
      FilePatternMatcher filePatterns);
}
