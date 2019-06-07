package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.*;
import io.reflectoring.coderadar.analyzer.port.driver.AnalyzeCommitUseCase;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import io.reflectoring.coderadar.vcs.port.driver.walk.findCommit.FindGitCommitCommand;
import io.reflectoring.coderadar.vcs.service.FindCommitService;
import io.reflectoring.coderadar.vcs.service.LocalGitRepositoryManager;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.BlobUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyzeCommitService implements AnalyzeCommitUseCase {

  private Logger logger = LoggerFactory.getLogger(AnalyzeCommitService.class);

  private final AnalyzerPluginService analyzerPluginService;
  private final FindCommitService findCommitService;
  private final AnalyzeFileService analyzeFileService;
  private final SaveCommitPort saveCommitPort;
  private final SaveMetricPort saveMetricPort;
  private LocalGitRepositoryManager gitRepoManager;

  @Autowired
  public AnalyzeCommitService(
      AnalyzerPluginService analyzerPluginService,
      FindCommitService findCommitService,
      AnalyzeFileService analyzeFileService,
      SaveCommitPort saveCommitPort,
      SaveMetricPort saveMetricPort,
      LocalGitRepositoryManager gitRepoManager) {
    this.analyzerPluginService = analyzerPluginService;
    this.findCommitService = findCommitService;
    this.analyzeFileService = analyzeFileService;
    this.saveCommitPort = saveCommitPort;
    this.saveMetricPort = saveMetricPort;
    this.gitRepoManager = gitRepoManager;
  }

  @Override
  public void analyzeCommit(Commit commit) {
    List<SourceCodeFileAnalyzerPlugin> analyzers = getAnalyzersForProject(commit.getProject());

    if (analyzers.isEmpty()) {
      logger.warn(
          "skipping analysis of commit {} since there are no analyzers configured for project {}!",
          commit.getName(),
          commit.getProject().getName());
      return;
    }

    int analyzedFiles = 0;
    Git gitClient = gitRepoManager.getLocalGitRepository(commit.getProject().getId());
    for (CommitToFileAssociation commitToFileAssociation : commit.getTouchedFiles()) {
      String filePath = commitToFileAssociation.getFile().getPath();
      FileMetrics fileMetrics = analyzeFile(gitClient, commit, filePath, analyzers);
      storeMetrics(commitToFileAssociation.getFile(), fileMetrics);
      commit.setAnalyzed(true);
      saveCommitPort.saveCommit(commit);
    }
  }

  private FileMetrics analyzeFile(
      Git gitClient, Commit commit, String filepath, List<SourceCodeFileAnalyzerPlugin> analyzers) {
    FindGitCommitCommand command = new FindGitCommitCommand(gitClient, commit.getName());
    RevCommit gitCommit = findCommitService.findCommit(command);
    byte[] fileContent =
        BlobUtils.getRawContent(gitClient.getRepository(), gitCommit.getId(), filepath);
    return analyzeFileService.analyzeFile(analyzers, filepath, fileContent);
  }

  private List<SourceCodeFileAnalyzerPlugin> getAnalyzersForProject(Project project) {
    List<SourceCodeFileAnalyzerPlugin> analyzers = new ArrayList<>();

    List<AnalyzerConfiguration> configs = project.getAnalyzerConfigurations();
    for (AnalyzerConfiguration config : configs) {
      analyzers.add(analyzerPluginService.createAnalyzer(config.getAnalyzerName()));
    }

    return analyzers;
  }

  private void storeMetrics(File file, FileMetrics fileMetrics) {
    for (io.reflectoring.coderadar.plugin.api.Metric metric : fileMetrics.getMetrics()) {
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
              null,
              file,
              new io.reflectoring.coderadar.analyzer.domain.Metric(null, metric.getId(), findings),
              fileMetrics.getMetricCount(metric));

      saveMetricPort.saveMetricValue(metricValue);
    }
  }
}
