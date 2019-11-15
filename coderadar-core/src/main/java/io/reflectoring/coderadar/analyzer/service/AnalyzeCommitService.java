package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Finding;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.analyzer.port.driver.AnalyzeCommitUseCase;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Metric;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.service.filepattern.FilePatternMatcher;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driver.GetCommitRawContentUseCase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeCommitService implements AnalyzeCommitUseCase {

  private final AnalyzeFileService analyzeFileService;
  private final GetCommitRawContentUseCase getCommitRawContentUseCase;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public AnalyzeCommitService(
      AnalyzeFileService analyzeFileService,
      GetCommitRawContentUseCase getCommitRawContentUseCase,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.analyzeFileService = analyzeFileService;
    this.getCommitRawContentUseCase = getCommitRawContentUseCase;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public List<MetricValue> analyzeCommit(
      Commit commit,
      Project project,
      List<SourceCodeFileAnalyzerPlugin> analyzers,
      FilePatternMatcher filePatterns) {
    List<MetricValue> metricValues = new ArrayList<>(400);
    List<String> filepaths =
        commit
            .getTouchedFiles()
            .stream()
            .map(fileToCommitRelationship -> fileToCommitRelationship.getFile().getPath())
            .filter(filePatterns::matches)
            .collect(Collectors.toList());
    analyzeBulk(commit, filepaths, analyzers, project)
        .forEach(
            (key, value) ->
                commit
                    .getTouchedFiles()
                    .stream()
                    .filter(relationship -> key.equals(relationship.getFile().getPath()))
                    .findFirst()
                    .ifPresent(
                        relationship -> {
                          Long fileId = relationship.getFile().getId();
                          metricValues.addAll(getMetrics(value, commit, fileId));
                        }));
    return metricValues;
  }

  private HashMap<String, FileMetrics> analyzeBulk(
      Commit commit,
      List<String> filepaths,
      List<SourceCodeFileAnalyzerPlugin> analyzers,
      Project project) {
    HashMap<String, FileMetrics> fileMetricsMap = new LinkedHashMap<>();
    try {
      HashMap<String, byte[]> fileContents =
          getCommitRawContentUseCase.getCommitContentBulk(
              coderadarConfigurationProperties.getWorkdir()
                  + "/projects/"
                  + project.getWorkdirName(),
              filepaths,
              commit.getName());
      fileContents.forEach(
          (key, value) ->
              fileMetricsMap.put(key, analyzeFileService.analyzeFile(analyzers, key, value)));
    } catch (UnableToGetCommitContentException e) {
      e.printStackTrace();
    }
    return fileMetricsMap;
  }

  private List<MetricValue> getMetrics(FileMetrics fileMetrics, Commit commit, Long fileId) {
    List<MetricValue> metricValues = new ArrayList<>();
    for (Metric metric : fileMetrics.getMetrics()) {
      List<Finding> findings =
          fileMetrics
              .getFindings(metric)
              .stream()
              .map(
                  finding ->
                      new Finding(
                          finding.getLineStart(),
                          finding.getLineEnd(),
                          finding.getCharStart(),
                          finding.getCharEnd()))
              .collect(Collectors.toList());
      MetricValue metricValue =
          new MetricValue(
              null, metric.getId(), fileMetrics.getMetricCount(metric), commit, findings, fileId);
      metricValues.add(metricValue);
    }
    return metricValues;
  }
}
