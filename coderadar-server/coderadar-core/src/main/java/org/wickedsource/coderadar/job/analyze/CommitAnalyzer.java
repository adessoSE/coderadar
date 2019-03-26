package org.wickedsource.coderadar.job.analyze;

import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gitective.core.BlobUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.analyzer.service.AnalyzerPluginRegistry;
import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFile;
import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationFileRepository;
import org.wickedsource.coderadar.analyzerconfig.domain.AnalyzerConfigurationRepository;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.file.domain.File;
import org.wickedsource.coderadar.file.domain.FileRepository;
import org.wickedsource.coderadar.file.domain.GitLogEntry;
import org.wickedsource.coderadar.file.domain.GitLogEntryRepository;
import org.wickedsource.coderadar.filepattern.domain.FilePatternRepository;
import org.wickedsource.coderadar.filepattern.domain.FileSetType;
import org.wickedsource.coderadar.filepattern.match.FilePatternMatcher;
import org.wickedsource.coderadar.job.LocalGitRepositoryManager;
import org.wickedsource.coderadar.metric.domain.finding.FindingRepository;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.plugin.*;
import org.wickedsource.coderadar.projectadministration.domain.AnalyzerConfiguration;
import org.wickedsource.coderadar.projectadministration.domain.FilePattern;
import org.wickedsource.coderadar.projectadministration.domain.Project;
import org.wickedsource.coderadar.vcs.git.GitCommitFinder;

@Service
public class CommitAnalyzer {

  private Logger logger = LoggerFactory.getLogger(CommitAnalyzer.class);

  private FilePatternRepository filePatternRepository;

  private GitCommitFinder commitFinder;

  private AnalyzerPluginRegistry analyzerRegistry;

  private AnalyzerConfigurationRepository analyzerConfigurationRepository;

  private AnalyzerConfigurationFileRepository analyzerConfigurationFileRepository;

  private FileAnalyzer fileAnalyzer;

  private FileRepository fileRepository;

  private MetricValueRepository metricValueRepository;

  private FindingRepository findingRepository;

  private Meter commitsMeter;

  private Meter filesMeter;

  private GitLogEntryRepository gitLogEntryRepository;

  private LocalGitRepositoryManager gitRepoManager;

  private CommitRepository commitRepository;

  private static final Set<ChangeType> CHANGES_TO_ANALYZE =
      EnumSet.of(ChangeType.ADD, ChangeType.COPY, ChangeType.MODIFY, ChangeType.RENAME);

  @Autowired
  public CommitAnalyzer(
      FilePatternRepository filePatternRepository,
      GitCommitFinder commitFinder,
      AnalyzerPluginRegistry analyzerRegistry,
      AnalyzerConfigurationRepository analyzerConfigurationRepository,
      AnalyzerConfigurationFileRepository analyzerConfigurationFileRepository,
      FileAnalyzer fileAnalyzer,
      FileRepository fileRepository,
      MetricValueRepository metricValueRepository,
      FindingRepository findingRepository,
      MetricRegistry metricRegistry,
      GitLogEntryRepository gitLogEntryRepository,
      LocalGitRepositoryManager gitRepoManager,
      CommitRepository commitRepository) {
    this.filePatternRepository = filePatternRepository;
    this.commitFinder = commitFinder;
    this.analyzerRegistry = analyzerRegistry;
    this.analyzerConfigurationRepository = analyzerConfigurationRepository;
    this.analyzerConfigurationFileRepository = analyzerConfigurationFileRepository;
    this.fileAnalyzer = fileAnalyzer;
    this.fileRepository = fileRepository;
    this.metricValueRepository = metricValueRepository;
    this.findingRepository = findingRepository;
    this.commitsMeter = metricRegistry.meter(name(CommitAnalyzer.class, "commits"));
    this.filesMeter = metricRegistry.meter(name(CommitAnalyzer.class, "files"));
    this.gitLogEntryRepository = gitLogEntryRepository;
    this.gitRepoManager = gitRepoManager;
    this.commitRepository = commitRepository;
  }

  public void analyzeCommit(Commit commit) {
    if (commit == null) {
      throw new IllegalArgumentException("argument 'commit' must not be null!");
    }

    List<SourceCodeFileAnalyzerPlugin> analyzers = getAnalyzersForProject(commit.getProject());

    if (analyzers.isEmpty()) {
      logger.warn(
          "skipping analysis of commit {} since there are no analyzers configured for project {}!",
          commit.getName(),
          commit.getProject().getName());
      return;
    }

    FilePatternMatcher matcher = getPatternMatcher(commit.getProject().getId());
    Git gitClient = gitRepoManager.getLocalGitRepository(commit.getProject().getId());
    List<GitLogEntry> logEntries =
        gitLogEntryRepository.findByCommitIdAndChangeTypeIn(commit.getId(), CHANGES_TO_ANALYZE);

    int analyzedFiles = 0;
    for (GitLogEntry logEntry : logEntries) {
      String filepath = logEntry.getFilepath();
      if (!matcher.matches(filepath)) {
        logger.trace(
            "skipping analysis of file {} in commit{} since it does not match the project's file patterns",
            filepath,
            commit.getName());
        continue;
      }

      logger.trace("analyzing file {} in commit {}", filepath, commit.getName());
      FileMetrics metrics = analyzeFile(gitClient, commit, filepath, analyzers);
      storeMetrics(commit, filepath, metrics);
      filesMeter.mark();
      analyzedFiles++;
    }

    commitsMeter.mark();
    commit.setAnalyzed(true);
    commitRepository.save(commit);
    logger.info(
        "analyzed {} files with {} analyzers in commit {}",
        analyzedFiles,
        analyzers.size(),
        commit.getName());
  }

  private FileMetrics analyzeFile(
      Git gitClient, Commit commit, String filepath, List<SourceCodeFileAnalyzerPlugin> analyzers) {
    RevCommit gitCommit = commitFinder.findCommit(gitClient, commit.getName());
    byte[] fileContent =
        BlobUtils.getRawContent(gitClient.getRepository(), gitCommit.getId(), filepath);
    return fileAnalyzer.analyzeFile(analyzers, filepath, fileContent);
  }

  private List<SourceCodeFileAnalyzerPlugin> getAnalyzersForProject(Project project) {
    List<SourceCodeFileAnalyzerPlugin> analyzers = new ArrayList<>();
    List<AnalyzerConfiguration> configs =
        analyzerConfigurationRepository.findByProjectId(project.getId());
    for (AnalyzerConfiguration config : configs) {
      AnalyzerConfigurationFile configFile =
          analyzerConfigurationFileRepository
              .findByAnalyzerConfigurationProjectIdAndAnalyzerConfigurationId(
                  project.getId(), config.getId());
      if (configFile != null) {
        analyzers.add(
            analyzerRegistry.createAnalyzer(config.getAnalyzerName(), configFile.getFileData()));
      } else {
        analyzers.add(analyzerRegistry.createAnalyzer(config.getAnalyzerName()));
      }
    }
    return analyzers;
  }

  private FilePatternMatcher getPatternMatcher(long projectId) {
    List<FilePattern> sourceFilePatterns =
        filePatternRepository.findByProjectIdAndFileSetType(projectId, FileSetType.SOURCE);
    return new FilePatternMatcher(sourceFilePatterns);
  }

  private void storeMetrics(Commit commit, String filePath, FileMetrics metrics) {
    File file =
        fileRepository.findInCommit(filePath, commit.getName(), commit.getProject().getId());

    if (file == null) {
      throw new IllegalStateException(
          String.format(
              "could not find file '%s' in commit %s (id: %d)",
              filePath, commit.getName(), commit.getId()));
    }

    for (Metric metric : metrics.getMetrics()) {
      MetricValueId id = new MetricValueId(commit, file, metric.getId());
      MetricValue metricValue = new MetricValue(id, metrics.getMetricCount(metric));
      metricValueRepository.save(metricValue);

      for (Finding finding : metrics.getFindings(metric)) {
        org.wickedsource.coderadar.metric.domain.finding.Finding entity =
            new org.wickedsource.coderadar.metric.domain.finding.Finding();
        entity.setId(id);
        entity.setLineStart(finding.getLineStart());
        entity.setLineEnd(finding.getLineEnd());
        entity.setCharStart(finding.getCharStart());
        entity.setLineEnd(finding.getLineEnd());
        findingRepository.save(entity);
      }
    }
  }
}
