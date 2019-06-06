package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service("StartAnalyzingService")
public class StartAnalyzingService implements StartAnalyzingUseCase {
  private final StartAnalyzingPort startAnalyzingPort;
  private final GetProjectPort getProjectPort;
  private final AnalyzeCommitService analyzeCommitService;
  private final CommitMetadataScanner commitMetadataScanner;
  private final TaskExecutor taskExecutor;

  @Autowired
  public StartAnalyzingService(
      @Qualifier("StartAnalyzingServiceNeo4j") StartAnalyzingPort startAnalyzingPort,
      GetProjectPort getProjectPort,
      AnalyzeCommitService analyzeCommitService,
      CommitMetadataScanner commitMetadataScanner,
      TaskExecutor taskExecutor) {
    this.startAnalyzingPort = startAnalyzingPort;
    this.getProjectPort = getProjectPort;
    this.analyzeCommitService = analyzeCommitService;
    this.commitMetadataScanner = commitMetadataScanner;
    this.taskExecutor = taskExecutor;
  }

  @Override
  public void start(StartAnalyzingCommand command, Long projectId) {
    Optional<Project> project = getProjectPort.get(projectId);
    if (!project.isPresent()) {
      throw new ProjectNotFoundException(projectId);
    }
    taskExecutor.execute(
        () -> {
          commitMetadataScanner.scan(project.get());
          List<Commit> commitsToBeAnalyzed = project.get().getCommits();
          for (Commit commit : commitsToBeAnalyzed) {
            if (!commit.isAnalyzed()) {
              analyzeCommitService.analyzeCommit(commit);
            }
          }
        });
  }
}
