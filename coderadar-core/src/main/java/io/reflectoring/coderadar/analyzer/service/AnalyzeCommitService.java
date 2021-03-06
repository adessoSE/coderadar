package io.reflectoring.coderadar.analyzer.service;

import com.google.common.collect.Maps;
import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.*;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Metric;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.LongToHashMapper;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driven.GetRawCommitContentPort;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** Performs analysis on a commit. */
@Service
@RequiredArgsConstructor
public class AnalyzeCommitService {

  private final AnalyzeFileService analyzeFileService;
  private final GetRawCommitContentPort getRawCommitContentPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  /**
   * Analyzes a single commit.
   *
   * @param commit The commit to analyze.
   * @param project The project the commit is part of.
   * @param analyzers The analyzers to use.
   * @return A list of metric values for the given commit.
   */
  public List<MetricValue> analyzeCommit(
      AnalyzeCommitDto commit, Project project, List<SourceCodeFileAnalyzerPlugin> analyzers) {
    List<MetricValue> metricValues = new ArrayList<>();
    analyzeBulk(commit.getHash(), commit.getChangedFiles(), analyzers, project)
        .forEach(
            (fileId, fileMetrics) ->
                metricValues.addAll(getMetrics(fileMetrics, commit.getId(), fileId)));
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
  private Map<Long, FileMetrics> analyzeBulk(
      long commitHash,
      AnalyzeFileDto[] files,
      List<SourceCodeFileAnalyzerPlugin> analyzers,
      Project project) {
    Map<Long, FileMetrics> fileMetricsMap = Maps.newLinkedHashMapWithExpectedSize(files.length);
    try {
      HashMap<AnalyzeFileDto, byte[]> fileContents =
          getRawCommitContentPort.getCommitContentBulkWithFiles(
              coderadarConfigurationProperties.getWorkdir()
                  + "/projects/"
                  + project.getWorkdirName(),
              files,
              LongToHashMapper.longToHash(commitHash));
      fileContents.forEach(
          (file, content) ->
              fileMetricsMap.put(
                  file.getId(),
                  analyzeFileService.analyzeFile(analyzers, file.getPath(), content)));
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
  private List<MetricValue> getMetrics(FileMetrics fileMetrics, long commitId, long fileId) {
    List<MetricValue> metricValues = new ArrayList<>(fileMetrics.getMetrics().size());
    for (Metric metric : fileMetrics.getMetrics()) {
      metricValues.add(
          new MetricValue(
              MetricNameMapper.mapToInt(metric.getId()),
              fileMetrics.getMetricCount(metric),
              commitId,
              fileId,
              fileMetrics.getFindings(metric)));
    }
    return metricValues;
  }
}
