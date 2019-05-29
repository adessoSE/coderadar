package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.port.driven.SaveCommitPort;
import io.reflectoring.coderadar.core.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.core.projectadministration.domain.Commit;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.vcs.port.driver.walk.findCommit.FindGitCommitCommand;
import io.reflectoring.coderadar.core.vcs.service.FindCommitService;
import io.reflectoring.coderadar.plugin.api.FileMetrics;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.BlobUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("AnalyzeCommitService")
public class AnalyzeCommitService {

  private Logger logger = LoggerFactory.getLogger(AnalyzeCommitService.class);

  private final AnalyzerService analyzerService;
  private final FindCommitService findCommitService;
  private final AnalyzeFileService analyzeFileService;
  private final SaveCommitPort saveCommitPort;
  private LocalGitRepositoryManager gitRepoManager;

  @Autowired
  public AnalyzeCommitService(
      AnalyzerService analyzerService,
      FindCommitService findCommitService,
      AnalyzeFileService analyzeFileService,
      SaveCommitPort saveCommitPort,
      LocalGitRepositoryManager gitRepoManager) {
    this.analyzerService = analyzerService;
    this.findCommitService = findCommitService;
    this.analyzeFileService = analyzeFileService;
    this.saveCommitPort = saveCommitPort;
    this.gitRepoManager = gitRepoManager;
  }

  public void analyzeCommit(Commit commit, Project project) {
    if (commit == null) {
      throw new IllegalArgumentException("argument 'commit' must not be null!");
    }

    List<SourceCodeFileAnalyzerPlugin> analyzers = getAnalyzersForProject(project);

    if (analyzers.isEmpty()) {
      logger.warn(
          "skipping analysis of commit {} since there are no analyzers configured for project {}!",
          commit.getName(),
          project.getName());
      return;
    }

    int analyzedFiles = 0;
    Git gitClient = gitRepoManager.getLocalGitRepository(project.getId());
    String filepath = commit.getTouchedFiles().get(0).getFile().getPath();
    FileMetrics fileMetrics = analyzeFile(gitClient, commit, filepath, analyzers);
    storeMetrics(commit, fileMetrics);
    commit.setAnalyzed(true);
    saveCommitPort.saveCommit(commit);
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
      analyzers.add(analyzerService.createAnalyzer(config.getAnalyzerName()));
    }

    return analyzers;
  }

  private void storeMetrics(Commit commit, FileMetrics fileMetrics) {}
}
