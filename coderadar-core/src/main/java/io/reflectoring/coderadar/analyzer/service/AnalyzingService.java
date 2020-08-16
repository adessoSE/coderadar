package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.MisconfigurationException;
import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.analyzer.port.driver.AnalyzeCommitUseCase;
import io.reflectoring.coderadar.analyzer.port.driver.GetAnalyzingStatusUseCase;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.analyzer.port.driver.StopAnalyzingUseCase;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.*;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.ListAnalyzerConfigurationsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.branch.ListBranchesPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AnalyzingService
    implements StartAnalyzingUseCase, StopAnalyzingUseCase, GetAnalyzingStatusUseCase {
  private final GetProjectPort getProjectPort;
  private final AnalyzeCommitUseCase analyzeCommitUseCase;
  private final AnalyzerPluginService analyzerPluginService;
  private final ListAnalyzerConfigurationsPort listAnalyzerConfigurationsPort;
  private final ListFilePatternsOfProjectPort listFilePatternsOfProjectPort;
  private final GetCommitsInProjectPort getCommitsInProjectPort;
  private final SaveMetricPort saveMetricPort;
  private final SaveCommitPort saveCommitPort;
  private final ProcessProjectService processProjectService;
  private final GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort;
  private final ListBranchesPort listBranchesPort;

  private final Map<Long, Boolean> activeAnalysis = new ConcurrentHashMap<>();

  private static final Logger logger = LoggerFactory.getLogger(AnalyzingService.class);

  public AnalyzingService(
      GetProjectPort getProjectPort,
      AnalyzeCommitUseCase analyzeCommitUseCase,
      AnalyzerPluginService analyzerPluginService,
      ListAnalyzerConfigurationsPort listAnalyzerConfigurationsPort,
      ListFilePatternsOfProjectPort listFilePatternsOfProjectPort,
      GetCommitsInProjectPort getCommitsInProjectPort,
      SaveMetricPort saveMetricPort,
      SaveCommitPort saveCommitPort,
      ProcessProjectService processProjectService,
      GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort,
      ListBranchesPort listBranchesPort) {
    this.getProjectPort = getProjectPort;
    this.analyzeCommitUseCase = analyzeCommitUseCase;
    this.analyzerPluginService = analyzerPluginService;
    this.listAnalyzerConfigurationsPort = listAnalyzerConfigurationsPort;
    this.listFilePatternsOfProjectPort = listFilePatternsOfProjectPort;
    this.getCommitsInProjectPort = getCommitsInProjectPort;
    this.saveMetricPort = saveMetricPort;
    this.saveCommitPort = saveCommitPort;
    this.processProjectService = processProjectService;
    this.getAvailableMetricsInProjectPort = getAvailableMetricsInProjectPort;
    this.listBranchesPort = listBranchesPort;
  }

  public void start(long projectId) {
    start(
        projectId,
        listBranchesPort.listBranchesInProject(projectId).stream()
            .map(Branch::getName)
            .collect(Collectors.toList()));
  }

  public void start(long projectId, List<String> branchNames) {
    Project project = getProjectPort.get(projectId);

    List<FilePattern> filePatterns = listFilePatternsOfProjectPort.listFilePatterns(projectId);
    List<AnalyzerConfiguration> analyzerConfigurations =
        listAnalyzerConfigurationsPort.listAnalyzerConfigurations(projectId);

    if (filePatterns.isEmpty()
        || filePatterns.stream()
            .noneMatch(
                filePattern -> filePattern.getInclusionType().equals(InclusionType.INCLUDE))) {
      throw new MisconfigurationException("Cannot analyze project without file patterns");
    } else if (analyzerConfigurations.isEmpty()
        || analyzerConfigurations.stream().noneMatch(AnalyzerConfiguration::isEnabled)) {
      throw new MisconfigurationException("Cannot analyze project without analyzers");
    }

    startAnalyzingTask(project, branchNames, filePatterns, analyzerConfigurations);
  }

  /**
   * Starts a background task using the TaskExecutor. It will perform the analysis of the project.
   *
   * @param project The project to analyze.
   * @param branches The branches to analyze.
   * @param filePatterns The patterns to use.
   * @param analyzerConfigurations The analyzer configurations to use.
   */
  private void startAnalyzingTask(
      Project project,
      List<String> branches,
      List<FilePattern> filePatterns,
      List<AnalyzerConfiguration> analyzerConfigurations) {
    processProjectService.executeTask(
        () -> {
          List<SourceCodeFileAnalyzerPlugin> sourceCodeFileAnalyzerPlugins =
              getAnalyzersForProject(analyzerConfigurations);
          activeAnalysis.put(project.getId(), true);
          for (String branch : branches) {
            if (Boolean.TRUE.equals(activeAnalysis.get(project.getId()))) {
              analyzeBranch(project, filePatterns, sourceCodeFileAnalyzerPlugins, branch);
            }
          }
          activeAnalysis.remove(project.getId());
          logger.info("Analysis complete for project {}", project.getName());
        },
        project.getId());
  }

  private void analyzeBranch(
      Project project,
      List<FilePattern> filePatterns,
      List<SourceCodeFileAnalyzerPlugin> sourceCodeFileAnalyzerPlugins,
      String branchName) {
    long projectId = project.getId();
    List<Commit> commits =
        getCommitsInProjectPort.getNonAnalyzedSortedByTimestampAscWithNoParents(
            projectId, filePatterns, branchName);
    if (commits.isEmpty() || sourceCodeFileAnalyzerPlugins.isEmpty()) {
      return;
    }
    List<Long> commitIds = new ArrayList<>();
    Map<Long, List<MetricValue>> fileMetrics =
        saveMetricPort.getMetricsForFiles(projectId, branchName);

    for (int i = 0; i < commits.size() && activeAnalysis.get(projectId); i++) {
      List<MetricValue> metrics =
          analyzeCommitUseCase.analyzeCommit(
              commits.get(i), project, sourceCodeFileAnalyzerPlugins);
      zeroOutMissingMetrics(commits.get(i), metrics, fileMetrics);
      if (!metrics.isEmpty()) {
        saveMetricPort.saveMetricValues(metrics);
        saveCommitPort.setCommitsWithIDsAsAnalyzed(commitIds);
        commitIds.clear();
      }
      commitIds.add(commits.get(i).getId());
      log(commits.get(i));
    }
    if (!getAvailableMetricsInProjectPort.get(projectId).isEmpty()) {
      saveCommitPort.setCommitsWithIDsAsAnalyzed(commitIds);
    }
  }

  /**
   * Checks if the files that have been changed in the current commit have missing metrics that
   * existed in previous commits. If so, sets them to zero.
   *
   * @param commit The currently analyzed commit.
   * @param metrics The metrics for the current commit.
   * @param fileMetrics All of the metrics gather for each file up until now.
   */
  private void zeroOutMissingMetrics(
      Commit commit, List<MetricValue> metrics, Map<Long, List<MetricValue>> fileMetrics) {

    for (FileToCommitRelationship relationship : commit.getTouchedFiles()) {
      List<MetricValue> values =
          fileMetrics.getOrDefault(relationship.getFile().getId(), Collections.emptyList());

      for (MetricValue value : values) {
        if (value.getValue() != 0
            && metrics.stream()
                .noneMatch(
                    metricValue ->
                        metricValue.getName().equals(value.getName())
                            && metricValue.getFileId() == value.getFileId())) {
          metrics.add(
              new MetricValue(
                  value.getName(), 0L, commit.getId(), value.getFileId(), Collections.emptyList()));
        }
      }
    }

    if (metrics.isEmpty()) {
      return;
    }
    metrics.sort(Comparator.comparingLong(MetricValue::getFileId));
    long fileId = metrics.get(0).getFileId();
    List<MetricValue> metricsForFile = new ArrayList<>();
    for (MetricValue metricValue : metrics) {
      if (metricValue.getValue() != 0) {
        if (metricValue.getFileId() != fileId) {
          fileMetrics.put(fileId, new ArrayList<>(metricsForFile));
          fileId = metricValue.getFileId();
          metricsForFile.clear();
        }
        metricsForFile.add(metricValue);
      }
    }
    fileMetrics.put(fileId, metricsForFile);
  }

  /**
   * Logs the successful analysis of a commit.
   *
   * @param commit The commit that was analyzed.
   */
  private void log(Commit commit) {
    logger.info("Analyzed commit {}", commit.getHash());
  }

  /**
   * Gets the configured analyzers for the current project and returns a list of
   * SourceCodeFileAnalyzerPlugin objects.
   *
   * @param analyzerConfigurations The analyzer configurations for the project.
   * @return A list of SourceCodeFileAnalyzerPlugins.
   */
  private List<SourceCodeFileAnalyzerPlugin> getAnalyzersForProject(
      List<AnalyzerConfiguration> analyzerConfigurations) {
    List<SourceCodeFileAnalyzerPlugin> analyzers = new ArrayList<>();
    for (AnalyzerConfiguration config : analyzerConfigurations) {
      if (config.isEnabled()) {
        analyzers.add(analyzerPluginService.createAnalyzer(config.getAnalyzerName()));
      }
    }
    return analyzers;
  }

  public void stop(long projectId) {
    activeAnalysis.put(projectId, false);
  }

  public boolean getStatus(long projectId) {
    return activeAnalysis.containsKey(projectId);
  }
}
