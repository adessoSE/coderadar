package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.vcs.port.driver.FindCommitUseCase;
import io.reflectoring.coderadar.vcs.port.driver.ProcessRepositoryUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StartAnalyzingService implements StartAnalyzingUseCase {
  private final StartAnalyzingPort startAnalyzingPort;
  private final GetProjectPort getProjectPort;
  private final AnalyzeCommitService analyzeCommitService;
  private final TaskExecutor taskExecutor;
  private final SaveCommitPort saveCommitPort;

  private final ProcessRepositoryUseCase processRepositoryUseCase;

  private final GetCommitsInProjectPort getCommitsInProjectPort;

  private final FindCommitUseCase findCommitUseCase;

  @Autowired
  public StartAnalyzingService(
      StartAnalyzingPort startAnalyzingPort,
      GetProjectPort getProjectPort,
      AnalyzeCommitService analyzeCommitService,
      TaskExecutor taskExecutor,
      SaveCommitPort saveCommitPort,
      ProcessRepositoryUseCase processRepositoryUseCase,
      GetCommitsInProjectPort getCommitsInProjectPort,
      FindCommitUseCase findCommitUseCase) {
    this.startAnalyzingPort = startAnalyzingPort;
    this.getProjectPort = getProjectPort;
    this.analyzeCommitService = analyzeCommitService;
    this.taskExecutor = taskExecutor;
    this.saveCommitPort = saveCommitPort;
    this.processRepositoryUseCase = processRepositoryUseCase;
    this.getCommitsInProjectPort = getCommitsInProjectPort;
    this.findCommitUseCase = findCommitUseCase;
  }

  @Override
  public void start(StartAnalyzingCommand command, Long projectId) {
    Project project = getProjectPort.get(projectId);
    taskExecutor.execute(
        () -> {
          List<Commit> commitsToBeAnalyzed = getCommitsInProjectPort.get(projectId);
          for (Commit commit : commitsToBeAnalyzed) {
            if (!commit.isAnalyzed()) {
              analyzeCommitService.analyzeCommit(commit, project);
            }
          }
        });
  }
}
