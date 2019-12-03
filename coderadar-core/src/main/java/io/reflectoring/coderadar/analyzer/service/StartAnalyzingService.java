package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.MisconfigurationException;
import io.reflectoring.coderadar.analyzer.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driven.StopAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.AnalyzeCommitUseCase;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.ProjectIsBeingProcessedException;
import io.reflectoring.coderadar.projectadministration.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.FilePattern;
import io.reflectoring.coderadar.projectadministration.domain.InclusionType;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.ListAnalyzerConfigurationsPort;
import io.reflectoring.coderadar.projectadministration.port.driven.filepattern.ListFilePatternsOfProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.ProjectStatusPort;
import io.reflectoring.coderadar.projectadministration.service.ProcessProjectService;
import io.reflectoring.coderadar.projectadministration.service.filepattern.FilePatternMatcher;
import io.reflectoring.coderadar.query.port.driven.GetAvailableMetricsInProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class StartAnalyzingService implements StartAnalyzingUseCase {
  private final GetProjectPort getProjectPort;
  private final AnalyzeCommitUseCase analyzeCommitUseCase;
  private final AnalyzerPluginService analyzerPluginService;
  private final ListAnalyzerConfigurationsPort listAnalyzerConfigurationsPort;
  private final ListFilePatternsOfProjectPort listFilePatternsOfProjectPort;
  private final GetCommitsInProjectPort getCommitsInProjectPort;
  private final SaveMetricPort saveMetricPort;
  private final SaveCommitPort saveCommitPort;
  private final ProcessProjectService processProjectService;
  private final StartAnalyzingPort startAnalyzingPort;
  private final StopAnalyzingPort stopAnalyzingPort;
  private final AsyncListenableTaskExecutor taskExecutor;
  private final GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort;
  private final ProjectStatusPort projectStatusPort;
  private final GetAnalyzingStatusService getAnalyzingStatusService;

  private final Logger logger = LoggerFactory.getLogger(StartAnalyzingService.class);

  public StartAnalyzingService(
      GetProjectPort getProjectPort,
      AnalyzeCommitUseCase analyzeCommitUseCase,
      AnalyzerPluginService analyzerPluginService,
      ListAnalyzerConfigurationsPort listAnalyzerConfigurationsPort,
      ListFilePatternsOfProjectPort listFilePatternsOfProjectPort,
      GetCommitsInProjectPort getCommitsInProjectPort,
      SaveMetricPort saveMetricPort,
      SaveCommitPort saveCommitPort,
      ProcessProjectService processProjectService,
      StartAnalyzingPort startAnalyzingPort,
      StopAnalyzingPort stopAnalyzingPort,
      AsyncListenableTaskExecutor taskExecutor,
      GetAvailableMetricsInProjectPort getAvailableMetricsInProjectPort,
      ProjectStatusPort projectStatusPort,
      GetAnalyzingStatusService getAnalyzingStatusService) {
    this.getProjectPort = getProjectPort;
    this.analyzeCommitUseCase = analyzeCommitUseCase;
    this.analyzerPluginService = analyzerPluginService;
    this.listAnalyzerConfigurationsPort = listAnalyzerConfigurationsPort;
    this.listFilePatternsOfProjectPort = listFilePatternsOfProjectPort;
    this.getCommitsInProjectPort = getCommitsInProjectPort;
    this.saveMetricPort = saveMetricPort;
    this.saveCommitPort = saveCommitPort;
    this.processProjectService = processProjectService;
    this.startAnalyzingPort = startAnalyzingPort;
    this.stopAnalyzingPort = stopAnalyzingPort;
    this.taskExecutor = taskExecutor;
    this.getAvailableMetricsInProjectPort = getAvailableMetricsInProjectPort;
    this.projectStatusPort = projectStatusPort;
    this.getAnalyzingStatusService = getAnalyzingStatusService;
  }

  /**
   * Starts the analysis of a project.
   *
   * @param command Command containing analysis parameters.
   * @param projectId The id of the project to analyze.
   */
  @Override
  public void start(StartAnalyzingCommand command, Long projectId) {
    if (projectStatusPort.isBeingProcessed(projectId)) {
      throw new ProjectIsBeingProcessedException(projectId);
    }
    List<FilePattern> filePatterns = listFilePatternsOfProjectPort.listFilePatterns(projectId);
    Collection<AnalyzerConfiguration> analyzerConfigurations =
        listAnalyzerConfigurationsPort.get(projectId);

    if (filePatterns.isEmpty()
        || filePatterns
            .stream()
            .noneMatch(
                filePattern -> filePattern.getInclusionType().equals(InclusionType.INCLUDE))) {
      throw new MisconfigurationException("Cannot analyze project without file patterns");
    } else if (analyzerConfigurations.isEmpty()
        || analyzerConfigurations
            .stream()
            .noneMatch(analyzerConfiguration -> analyzerConfiguration.getEnabled())) {
      throw new MisconfigurationException("Cannot analyze project without analyzers");
    }
    startAnalyzingTask(command, projectId, filePatterns);
  }

  /**
   * Starts a background task using the TaskExecutor. It will perform the analysis of the project.
   *
   * @param command The analysing command to use.
   * @param projectId The id of the project to analyze.
   * @param filePatterns The patterns to use.
   */
  private void startAnalyzingTask(
      StartAnalyzingCommand command, Long projectId, List<FilePattern> filePatterns) {
    processProjectService.executeTask(
        () -> {
          List<SourceCodeFileAnalyzerPlugin> sourceCodeFileAnalyzerPlugins =
              getAnalyzersForProject(projectId);
          Project project = getProjectPort.get(projectId);
          List<Commit> commitsToBeAnalyzed =
              getCommitsInProjectPort.getSortedByTimestampAscWithNoParents(projectId);
          Long[] commitIds = new Long[commitsToBeAnalyzed.size()];
          FilePatternMatcher filePatternMatcher = new FilePatternMatcher(filePatterns);

          startAnalyzingPort.start(command, projectId);
          int counter = 0;
          ListenableFuture<?> saveTask = null;
          for (Commit commit : commitsToBeAnalyzed) {
            if (!commit.isAnalyzed() && getAnalyzingStatusService.get(projectId)) {
              List<MetricValue> metrics =
                  analyzeCommitUseCase.analyzeCommit(
                      commit, project, sourceCodeFileAnalyzerPlugins, filePatternMatcher);
              if (!metrics.isEmpty()) {
                waitForTask(saveTask);
                saveTask =
                    taskExecutor.submitListenable(
                        () -> {
                          saveMetricPort.saveMetricValues(metrics, projectId);
                          saveCommitPort.setCommitsWithIDsAsAnalyzed(commitIds);
                        });
              }
              commitIds[counter] = commit.getId();
              ++counter;
              log(commit, counter);
            }
          }
          waitForTask(saveTask);
          stopAnalyzingPort.stop(projectId);
          if (!getAvailableMetricsInProjectPort.get(projectId).isEmpty()) {
            saveCommitPort.setCommitsWithIDsAsAnalyzed(commitIds);
          }

          logger.info("Analysis complete for project {}", project.getName());
        },
        projectId);
  }

  /**
   * Logs the successful analysis of a commit.
   *
   * @param commit The commit that was analyzed.
   * @param counter The number of the commit.
   */
  private void log(Commit commit, int counter) {
    logger.info(
        "Analyzed commit: {} {}, total analyzed: {}",
        commit.getComment(),
        commit.getName(),
        counter);
  }

  /**
   * Waits for the save task to complete.
   *
   * @param saveTask The saveTask object.
   */
  private void waitForTask(ListenableFuture<?> saveTask) {
    if (saveTask != null) {
      try {
        saveTask.get();
      } catch (InterruptedException | ExecutionException ignored) {
      }
    }
  }

  /**
   * Gets the configured analyzers for the current project and returns a list of
   * SourceCodeFileAnalyzerPlugin objects.
   *
   * @param projectId The id of the project.
   * @return A list of SourceCodeFileAnalyzerPlugins.
   */
  private List<SourceCodeFileAnalyzerPlugin> getAnalyzersForProject(Long projectId) {
    List<SourceCodeFileAnalyzerPlugin> analyzers = new ArrayList<>();
    Collection<AnalyzerConfiguration> configs = listAnalyzerConfigurationsPort.get(projectId);
    for (AnalyzerConfiguration config : configs) {
      if (config.getEnabled()) {
        analyzers.add(analyzerPluginService.createAnalyzer(config.getAnalyzerName()));
      }
    }
    return analyzers;
  }
}
