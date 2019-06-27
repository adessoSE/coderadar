package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.*;
import io.reflectoring.coderadar.analyzer.port.driver.AnalyzeCommitUseCase;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.Metric;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.vcs.UnableToGetCommitContentException;
import io.reflectoring.coderadar.vcs.port.driver.GetCommitRawContentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyzeCommitService implements AnalyzeCommitUseCase {

  private Logger logger = LoggerFactory.getLogger(AnalyzeCommitService.class);

  private final AnalyzerPluginService analyzerPluginService;
  private final AnalyzeFileService analyzeFileService;
  private final SaveCommitPort saveCommitPort;
  private final SaveMetricPort saveMetricPort;
  private final GetCommitRawContentUseCase getCommitRawContentUseCase;
  private final UpdateProjectPort updateProjectPort;

  @Autowired
  public AnalyzeCommitService(
      AnalyzerPluginService analyzerPluginService,
      AnalyzeFileService analyzeFileService,
      SaveCommitPort saveCommitPort,
      SaveMetricPort saveMetricPort,
      GetCommitRawContentUseCase getCommitRawContentUseCase,
      UpdateProjectPort updateProjectPort) {
    this.analyzerPluginService = analyzerPluginService;
    this.analyzeFileService = analyzeFileService;
    this.saveCommitPort = saveCommitPort;
    this.saveMetricPort = saveMetricPort;
    this.getCommitRawContentUseCase = getCommitRawContentUseCase;
    this.updateProjectPort = updateProjectPort;
  }

  @Override
  public void analyzeCommit(Commit commit, Project project) {
    List<SourceCodeFileAnalyzerPlugin> analyzers = getAnalyzersForProject(project);
    if (analyzers.isEmpty()) {
      logger.warn(
          "skipping analysis of commit {} since there are no analyzers configured for project {}!",
          commit.getName(),
          project.getName());
    } else {
      int analyzedFiles = 0;
      for (FileToCommitRelationship fileToCommitRelationship : commit.getTouchedFiles()) {
        String filePath = fileToCommitRelationship.getFile().getPath();
        // project.getFiles().add(fileToCommitRelationship.getFile());
        FileMetrics fileMetrics = analyzeFile(commit, filePath, analyzers);
        storeMetrics(fileToCommitRelationship.getFile(), fileMetrics, commit);
        commit.setAnalyzed(true);
        saveCommitPort.saveCommit(commit);
      }
      updateProjectPort.update(project);
    }
  }

  private FileMetrics analyzeFile(
      Commit commit, String filepath, List<SourceCodeFileAnalyzerPlugin> analyzers) {
    byte[] fileContent = new byte[0];
    try {
      fileContent = getCommitRawContentUseCase.getCommitContent(filepath, commit.getName());
    } catch (UnableToGetCommitContentException e) {
      e.printStackTrace();
    }
    return analyzeFileService.analyzeFile(analyzers, filepath, fileContent);
  }

  private List<SourceCodeFileAnalyzerPlugin> getAnalyzersForProject(Project project) {
    List<SourceCodeFileAnalyzerPlugin> analyzers = new ArrayList<>();

    // TODO: USE CASE + PORT
    /*    List<AnalyzerConfiguration> configs = project.getAnalyzerConfigurations();
    for (AnalyzerConfiguration config : configs) {
      analyzers.add(analyzerPluginService.createAnalyzer(config.getAnalyzerName()));
    }*/

    return analyzers;
  }

  private void storeMetrics(File file, FileMetrics fileMetrics, Commit commit) {
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
      saveMetricPort.saveMetricValue(metricValue);
    }
  }
}
