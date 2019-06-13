package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.analyzer.port.driven.StartAnalyzingPort;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingCommand;
import io.reflectoring.coderadar.analyzer.port.driver.StartAnalyzingUseCase;
import io.reflectoring.coderadar.analyzer.service.filter.LastKnownCommitFilter;
import io.reflectoring.coderadar.projectadministration.ProjectNotFoundException;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.GetProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.vcs.UnableToProcessRepositoryException;
import io.reflectoring.coderadar.vcs.domain.VcsCommit;
import io.reflectoring.coderadar.vcs.port.driver.FindCommitUseCase;
import io.reflectoring.coderadar.vcs.port.driver.ProcessRepositoryUseCase;
import java.nio.file.Paths;
import java.util.*;
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
    Optional<Project> project = getProjectPort.get(projectId);
    if (!project.isPresent()) {
      throw new ProjectNotFoundException(projectId);
    }
    taskExecutor.execute(
        () -> {
          Commit lastKnownCommit =
              getCommitsInProjectPort.findTop1ByProjectIdOrderBySequenceNumberDesc(projectId);
          Collection<Commit> commitsToSave = new ArrayList<>();
          try {
            processRepositoryUseCase.processRepository(
                Paths.get(project.get().getWorkdirName()),
                (VcsCommit vcsCommit) -> {
                  Commit commit = new Commit();
                  commit.setName(vcsCommit.getName());
                  commit.setAuthor(vcsCommit.getAuthor());
                  commit.setComment(vcsCommit.getMessage());
                  commit.setTimestamp(new Date(vcsCommit.getCommitTime() * 1000L));
                  commit.setSequenceNumber(vcsCommit.getSequenceNumber());
                  /* TODO: Get a commit hash?? from the vcs ports
                  commit.setParents(vcsCommit.getParents());
                  */
                  commitsToSave.add(commit);
                },
                new LastKnownCommitFilter(
                    Paths.get(project.get().getWorkdirName()), findCommitUseCase, null));
          } catch (UnableToProcessRepositoryException e) {
            // TODO
          }
          saveCommitPort.saveCommits(commitsToSave);

          List<Commit> commitsToBeAnalyzed = getCommitsInProjectPort.get(projectId);
          for (Commit commit : commitsToBeAnalyzed) {
            if (!commit.isAnalyzed()) {
              analyzeCommitService.analyzeCommit(commit, project.get());
            }
          }
        });
  }
}
