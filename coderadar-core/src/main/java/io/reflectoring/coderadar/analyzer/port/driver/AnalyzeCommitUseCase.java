package io.reflectoring.coderadar.analyzer.port.driver;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.service.filepattern.FilePatternMatcher;

import java.util.List;

public interface AnalyzeCommitUseCase {
  List<MetricValue> analyzeCommit(Commit commit, Project project, List<SourceCodeFileAnalyzerPlugin> analyzers, FilePatternMatcher filePatterns);
}
