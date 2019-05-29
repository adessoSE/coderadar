package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Commit;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("StartAnalyzingService")
public class StartAnalyzingService implements StartAnalyzingUseCase {
  private final StartAnalyzingPort startAnalyzingPort;
  private final GetProjectPort getProjectPort;
  private final AnalyzeCommitService analyzeCommitService;

  @Autowired
  public StartAnalyzingService(
      @Qualifier("StartAnalyzingServiceNeo4j") StartAnalyzingPort startAnalyzingPort,
      GetProjectPort getProjectPort,
      AnalyzeCommitService analyzeCommitService) {
    this.startAnalyzingPort = startAnalyzingPort;
    this.getProjectPort = getProjectPort;
    this.analyzeCommitService = analyzeCommitService;
  }

  @Override
  public Long start(StartAnalyzingCommand command) {
    Optional<Project> project = getProjectPort.get(command.getProjectId());
    if (!project.isPresent()) {
      throw new ProjectNotFoundException(command.getProjectId());
    }

    List<Commit> commitsToBeAnalyzed = project.get().getCommits();
    for (Commit commit : commitsToBeAnalyzed) {
      analyzeCommitService.analyzeCommit(commit, project.get());
    }
    return startAnalyzingPort.start(command);
  }
}
