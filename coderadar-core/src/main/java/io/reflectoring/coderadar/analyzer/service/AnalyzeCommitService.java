package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.FileToCommitRelationship;
import io.reflectoring.coderadar.analyzer.domain.Finding;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.analyzer.port.driver.AnalyzeCommitUseCase;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Metric;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.service.filepattern.FilePatternMatcher;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driver.GetCommitRawContentUseCase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeCommitService implements AnalyzeCommitUseCase {

  private Logger logger = LoggerFactory.getLogger(AnalyzeCommitService.class);

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
    List<MetricValue> metricValues = new ArrayList<>();
    if (analyzers.isEmpty()) {
      logger.warn(
          "skipping analysis of commit {} since there are no analyzers configured for project {}!",
          commit.getName(),
          project.getName());
    } else {
      List<String> filepaths = commit.getTouchedFiles().stream()
              .map(fileToCommitRelationship -> fileToCommitRelationship.getFile().getPath()).collect(Collectors.toList());
      analyzeBulk(commit, filepaths, analyzers, project).forEach((key, value) -> commit.getTouchedFiles().stream()
              .filter(relationship -> key.equals(relationship.getFile().getPath())).findFirst().ifPresent(relationship -> {
        Long fileId = relationship.getFile().getId();
        if (filePatterns.matches(key)) {
          metricValues.addAll(getMetrics(value, commit, fileId));
        }
      }));
      commit.setAnalyzed(true);
    }
    return metricValues;
  }

  private HashMap<String, FileMetrics> analyzeBulk (
          Commit commit,
          List<String> filepaths,
          List<SourceCodeFileAnalyzerPlugin> analyzers,
          Project project) {
    HashMap<String, FileMetrics> fileMetricsMap = new LinkedHashMap<>();
      try {
        HashMap<String, byte[]> fileContents = getCommitRawContentUseCase.getCommitContentBulk(
                coderadarConfigurationProperties.getWorkdir()
                        + "/projects/"
                        + project.getWorkdirName(),
                filepaths,
                commit.getName()
        );
        fileContents.forEach((key, value) -> fileMetricsMap.put(key, analyzeFileService.analyzeFile(analyzers, key, value)));
      } catch (UnableToGetCommitContentException e) {
        e.printStackTrace();
      }
    return fileMetricsMap;
  }

  private FileMetrics analyzeFile(
      Commit commit,
      String filepath,
      List<SourceCodeFileAnalyzerPlugin> analyzers,
      Project project) {
    byte[] fileContent = new byte[0];
    try {
      fileContent =
          getCommitRawContentUseCase.getCommitContent(
              coderadarConfigurationProperties.getWorkdir()
                  + "/projects/"
                  + project.getWorkdirName(),
              filepath,
              commit.getName());
    } catch (UnableToGetCommitContentException e) {
      e.printStackTrace();
    }
    return analyzeFileService.analyzeFile(analyzers, filepath, fileContent);
  }

  private List<MetricValue> getMetrics(FileMetrics fileMetrics, Commit commit, Long fileId) {
    List<MetricValue> metricValues = new ArrayList<>();
    for (Metric metric : fileMetrics.getMetrics()) {
      List<Finding> findings = new ArrayList<>();
      for (io.reflectoring.coderadar.plugin.api.Finding finding : fileMetrics.getFindings(metric)) {
        Finding entity = new Finding();

        entity.setLineStart(finding.getLineStart());
        entity.setLineEnd(finding.getLineEnd());
        entity.setCharStart(finding.getCharStart());
        entity.setLineEnd(finding.getLineEnd());

        findings.add(entity);
      }
      MetricValue metricValue =
          new MetricValue(
              null, metric.getId(), fileMetrics.getMetricCount(metric), commit, findings, fileId);

      metricValues.add(metricValue);
    }
    return metricValues;
  }
}
