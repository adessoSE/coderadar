package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.CoderadarConfigurationProperties;
import io.reflectoring.coderadar.analyzer.domain.*;
import io.reflectoring.coderadar.analyzer.port.driver.AnalyzeCommitUseCase;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Metric;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driver.GetCommitRawContentUseCase;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeCommitService implements AnalyzeCommitUseCase {

  private Logger logger = LoggerFactory.getLogger(AnalyzeCommitService.class);

  private final AnalyzerPluginService analyzerPluginService;
  private final AnalyzeFileService analyzeFileService;
  private final SaveCommitPort saveCommitPort;
  private final SaveMetricPort saveMetricPort;
  private final GetCommitRawContentUseCase getCommitRawContentUseCase;
  private final UpdateProjectPort updateProjectPort;
  private final GetAnalyzerConfigurationsFromProjectPort getAnalyzerConfigurationsFromProjectPort;
  private final CoderadarConfigurationProperties coderadarConfigurationProperties;

  @Autowired
  public AnalyzeCommitService(
      AnalyzerPluginService analyzerPluginService,
      AnalyzeFileService analyzeFileService,
      SaveCommitPort saveCommitPort,
      SaveMetricPort saveMetricPort,
      GetCommitRawContentUseCase getCommitRawContentUseCase,
      UpdateProjectPort updateProjectPort,
      GetAnalyzerConfigurationsFromProjectPort getAnalyzerConfigurationsFromProjectPort,
      CoderadarConfigurationProperties coderadarConfigurationProperties) {
    this.analyzerPluginService = analyzerPluginService;
    this.analyzeFileService = analyzeFileService;
    this.saveCommitPort = saveCommitPort;
    this.saveMetricPort = saveMetricPort;
    this.getCommitRawContentUseCase = getCommitRawContentUseCase;
    this.updateProjectPort = updateProjectPort;
    this.getAnalyzerConfigurationsFromProjectPort = getAnalyzerConfigurationsFromProjectPort;
    this.coderadarConfigurationProperties = coderadarConfigurationProperties;
  }

  @Override
  public List<MetricValue> analyzeCommit(
      Commit commit, Project project, List<SourceCodeFileAnalyzerPlugin> analyzers) {
    List<MetricValue> metricValues = new ArrayList<>();
    if (analyzers.isEmpty()) {
      logger.warn(
          "skipping analysis of commit {} since there are no analyzers configured for project {}!",
          commit.getName(),
          project.getName());
    } else {
      for (FileToCommitRelationship fileToCommitRelationship : commit.getTouchedFiles()) {
        String filePath = fileToCommitRelationship.getFile().getPath();
        FileMetrics fileMetrics = analyzeFile(commit, filePath, analyzers, project);
        commit.setAnalyzed(true);
        metricValues.addAll(getMetrics(fileMetrics, commit));
      }
    }
    logger.info(String.format("Analyzed commit %s %s", commit.getComment(), commit.getName()));
    return metricValues;
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

  private List<MetricValue> getMetrics(FileMetrics fileMetrics, Commit commit) {
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
              null, metric.getId(), fileMetrics.getMetricCount(metric), commit, findings);

      metricValues.add(metricValue);
    }
    return metricValues;
  }
}
