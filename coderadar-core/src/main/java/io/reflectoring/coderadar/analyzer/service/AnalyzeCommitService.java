package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Finding;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.analyzer.port.driver.AnalyzeCommitUseCase;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Metric;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.File;
import io.reflectoring.coderadar.projectadministration.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.stereotype.Service;

/** Performs analysis on a commit. */
@Service
public class AnalyzeCommitService implements AnalyzeCommitUseCase {

  private final AnalyzeFileService analyzeFileService;
  private final GetRawCommitContentPort getRawCommitContentPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  public AnalyzeCommitService(
      AnalyzeFileService analyzeFileService,
      GetRawCommitContentPort getRawCommitContentPort,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.analyzeFileService = analyzeFileService;
    this.getRawCommitContentPort = getRawCommitContentPort;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  /**
   * Analyzes a single commit.
   *
   * @param commit The commit to analyze.
   * @param project The project the commit is part of.
   * @param analyzers The analyzers to use.
   * @return A list of metric values for the given commit.
   */
  @Override
  public List<MetricValue> analyzeCommit(
      Commit commit, Project project, List<SourceCodeFileAnalyzerPlugin> analyzers) {
    List<MetricValue> metricValues = new ArrayList<>();
    List<File> files = new ArrayList<>(commit.getTouchedFiles().size());
    for (FileToCommitRelationship ftr : commit.getTouchedFiles()) {
      files.add(ftr.getFile());
    }

    analyzeBulk(commit.getName(), files, analyzers, project)
        .forEach(
            (file, fileMetrics) ->
                metricValues.addAll(getMetrics(fileMetrics, commit.getId(), file.getId())));
    return metricValues;
  }

  /**
   * Analyzes all files of a commit in bulk.
   *
   * @param commitHash The commit hash.
   * @param files The files of the commit.
   * @param analyzers The analyzers to use.
   * @param project The project the commit is in.
   * @return A map of File and corresponding FileMetrics
   */
  private HashMap<File, FileMetrics> analyzeBulk(
      String commitHash,
      List<File> files,
      List<SourceCodeFileAnalyzerPlugin> analyzers,
      Project project) {
    HashMap<File, FileMetrics> fileMetricsMap = new LinkedHashMap<>();
    try {
      HashMap<File, byte[]> fileContents =
          getRawCommitContentPort.getCommitContentBulkWithFiles(
              coderadarConfigurationProperties.getWorkdir()
                  + "/projects/"
                  + project.getWorkdirName(),
              files,
              commitHash);
      fileContents.forEach(
          (key, value) ->
              fileMetricsMap.put(
                  key, analyzeFileService.analyzeFile(analyzers, key.getPath(), value)));
    } catch (UnableToGetCommitContentException e) {
      e.printStackTrace();
    }
    return fileMetricsMap;
  }

  /**
   * Extracts the metrics out of the FileMetrics (plugin) objects and returns a list of MetricValues
   *
   * @param fileMetrics The file metrics to use.
   * @param commitId The DB id of the commit.
   * @param fileId The DB id of the current file.
   * @return A list of MetricValues.
   */
  private List<MetricValue> getMetrics(FileMetrics fileMetrics, Long commitId, Long fileId) {
    List<MetricValue> metricValues = new ArrayList<>();
    for (Metric metric : fileMetrics.getMetrics()) {
      List<Finding> findings = new ArrayList<>();
      for (io.reflectoring.coderadar.plugin.api.Finding finding : fileMetrics.getFindings(metric)) {
        findings.add(
            new Finding(
                finding.getLineStart(),
                finding.getLineEnd(),
                finding.getCharStart(),
                finding.getCharEnd(),
                finding.getMessage()));
      }
      metricValues.add(
          new MetricValue(
              metric.getId(), fileMetrics.getMetricCount(metric), commitId, fileId, findings));
    }
    return metricValues;
  }
}
