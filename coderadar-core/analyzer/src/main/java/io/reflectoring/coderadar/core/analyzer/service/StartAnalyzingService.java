package io.reflectoring.coderadar.core.analyzer.service;

import io.reflectoring.coderadar.core.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.core.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.core.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.core.projectadministration.domain.Commit;
import io.reflectoring.coderadar.core.projectadministration.domain.Project;
import io.reflectoring.coderadar.core.projectadministration.port.driven.project.GetProjectPort;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("StartAnalyzingService")
public class StartAnalyzingService implements StartAnalyzingUseCase {
  private final StartAnalyzingPort startAnalyzingPort;
  private final GetProjectPort getProjectPort;
  private final AnalyzeCommitService analyzeCommitService;
  private final CommitMetadataScanner commitMetadataScanner;

  @Autowired
  public StartAnalyzingService(
      @Qualifier("StartAnalyzingServiceNeo4j") StartAnalyzingPort startAnalyzingPort,
      GetProjectPort getProjectPort,
      AnalyzeCommitService analyzeCommitService,
      CommitMetadataScanner commitMetadataScanner) {
    this.startAnalyzingPort = startAnalyzingPort;
    this.getProjectPort = getProjectPort;
    this.analyzeCommitService = analyzeCommitService;
    this.commitMetadataScanner = commitMetadataScanner;
  }

  @Override
  public void start(StartAnalyzingCommand command, Long projectId) {
    Optional<Project> project = getProjectPort.get(projectId);
    if (!project.isPresent()) {
      throw new ProjectNotFoundException(projectId);
    }
    commitMetadataScanner.scan(project.get());
    List<Commit> commitsToBeAnalyzed = project.get().getCommits();
    for (Commit commit : commitsToBeAnalyzed) {
      if (!commit.isAnalyzed()) {
        analyzeCommitService.analyzeCommit(commit);
      }
    }
  }
}
