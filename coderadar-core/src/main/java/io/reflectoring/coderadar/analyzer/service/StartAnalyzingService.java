package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.domain.MetricValue;
import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.plugin.api.SourceCodeFileAnalyzerPlugin;
import io.reflectoring.coderadar.projectadministration.domain.AnalyzerConfiguration;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveMetricPort;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzerconfig.GetAnalyzerConfigurationsFromProjectPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.vcs.port.driver.FindCommitUseCase;
import io.reflectoring.coderadar.vcs.port.driver.ProcessRepositoryUseCase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class StartAnalyzingService implements StartAnalyzingUseCase {
  private final StartAnalyzingPort startAnalyzingPort;
  private final GetProjectPort getProjectPort;
  private final AnalyzeCommitService analyzeCommitService;
  private final TaskExecutor taskExecutor;
  private final SaveCommitPort saveCommitPort;
  private final AnalyzerPluginService analyzerPluginService;

  private final ProcessRepositoryUseCase processRepositoryUseCase;
  private final GetAnalyzerConfigurationsFromProjectPort getAnalyzerConfigurationsFromProjectPort;

  private final GetCommitsInProjectPort getCommitsInProjectPort;

  private final FindCommitUseCase findCommitUseCase;
  private final SaveMetricPort saveMetricPort;

  @Autowired
  public StartAnalyzingService(
          StartAnalyzingPort startAnalyzingPort,
          GetProjectPort getProjectPort,
          AnalyzeCommitService analyzeCommitService,
          TaskExecutor taskExecutor,
          SaveCommitPort saveCommitPort,
          AnalyzerPluginService analyzerPluginService, ProcessRepositoryUseCase processRepositoryUseCase,
          GetAnalyzerConfigurationsFromProjectPort getAnalyzerConfigurationsFromProjectPort, GetCommitsInProjectPort getCommitsInProjectPort,
          FindCommitUseCase findCommitUseCase, SaveMetricPort saveMetricPort) {
    this.startAnalyzingPort = startAnalyzingPort;
    this.getProjectPort = getProjectPort;
    this.analyzeCommitService = analyzeCommitService;
    this.taskExecutor = taskExecutor;
    this.saveCommitPort = saveCommitPort;
    this.analyzerPluginService = analyzerPluginService;
    this.processRepositoryUseCase = processRepositoryUseCase;
    this.getAnalyzerConfigurationsFromProjectPort = getAnalyzerConfigurationsFromProjectPort;
    this.getCommitsInProjectPort = getCommitsInProjectPort;
    this.findCommitUseCase = findCommitUseCase;
    this.saveMetricPort = saveMetricPort;
  }

  @Override
  public void start(StartAnalyzingCommand command, Long projectId) {
    Project project = getProjectPort.get(projectId);
    taskExecutor.execute(
        () -> {
          List<Commit> commitsToBeAnalyzed = getCommitsInProjectPort.get(projectId);
          List<MetricValue> metricValues = new ArrayList<>();
          List<SourceCodeFileAnalyzerPlugin> sourceCodeFileAnalyzerPlugins = getAnalyzersForProject(project);
          for (Commit commit : commitsToBeAnalyzed) {
            if (!commit.isAnalyzed()) {
              metricValues.addAll(analyzeCommitService.analyzeCommit(commit, project, sourceCodeFileAnalyzerPlugins));
            }
          }
          saveMetricPort.saveMetricValues(metricValues);
        });
  }

  private List<SourceCodeFileAnalyzerPlugin> getAnalyzersForProject(Project project) {
    List<SourceCodeFileAnalyzerPlugin> analyzers = new ArrayList<>();

    Collection<AnalyzerConfiguration> configs = getAnalyzerConfigurationsFromProjectPort.get(project.getId());
    for (AnalyzerConfiguration config : configs) {
      analyzers.add(analyzerPluginService.createAnalyzer(config.getAnalyzerName()));
    }

    return analyzers;
  }
}
