package io.reflectoring.coderadar.analyzer.service;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.projectadministration.domain.Project;
import io.reflectoring.coderadar.projectadministration.port.driven.analyzer.SaveCommitPort;
import io.reflectoring.coderadar.projectadministration.port.driven.project.UpdateProjectPort;
import io.reflectoring.coderadar.query.port.driven.GetCommitsInProjectPort;
import io.reflectoring.coderadar.vcs.port.driver.walk.findCommit.FindGitCommitUseCase;
import io.reflectoring.coderadar.vcs.port.driver.walk.walkCommit.WalkCommitsCommand;
import io.reflectoring.coderadar.vcs.port.driver.walk.walkCommit.WalkCommitsUseCase;
import io.reflectoring.coderadar.vcs.service.LocalGitRepositoryManager;
import io.reflectoring.coderadar.vcs.service.walk.filter.DateRangeCommitFilter;
import io.reflectoring.coderadar.vcs.service.walk.filter.LastKnownCommitFilter;
import java.io.File;
import java.time.LocalDate;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.stereotype.Service;

@Service
public class CommitMetadataScanner {

  private Logger logger = LoggerFactory.getLogger(CommitMetadataScanner.class);

  private final SaveCommitPort saveCommitPort;

  private final LocalGitRepositoryManager gitRepoManager;

  // private Meter commitsMeter;

  private final WalkCommitsUseCase walker;

  private final FindGitCommitUseCase findGitCommitUseCase;

  private final GetCommitsInProjectPort getCommitsInProjectPort;

  private UpdateProjectPort updateProjectPort;

  @Autowired
  public CommitMetadataScanner(
      SaveCommitPort saveCommitPort,
      LocalGitRepositoryManager gitRepoManager,
      // MetricRegistry metricRegistry,
      WalkCommitsUseCase walker,
      FindGitCommitUseCase findGitCommitUseCase,
      GetCommitsInProjectPort getCommitsInProjectPort,
      UpdateProjectPort updateProjectPort) {
    this.saveCommitPort = saveCommitPort;
    this.gitRepoManager = gitRepoManager;

    this.walker = walker;
    this.findGitCommitUseCase = findGitCommitUseCase;
    this.getCommitsInProjectPort = getCommitsInProjectPort;
    this.updateProjectPort = updateProjectPort;
  }

  /**
   * Scans the local GIT repository of the specified project and stores metadata about each commit
   * in the database. If the local GIT repository does not exist, the remote repository of the
   * project is cloned into a local repository first. If it exists, it will be updated to the state
   * of the remote repository before scanning.
   *
   * @param project he project whose repository to scan.
   * @return File object of the local GIT repository.
   */
  public File scan(Project project) {
    Git gitClient = gitRepoManager.updateLocalGitRepository(project);
    scanLocalRepository(project, gitClient);
    return gitClient.getRepository().getDirectory();
  }

  private void scanLocalRepository(Project project, Git gitClient) {
    Commit lastKnownCommit =
        getCommitsInProjectPort.findTop1ByProjectIdOrderBySequenceNumberDesc(project.getId());

    // start at the next unknown commit
    if (lastKnownCommit != null) {
      LastKnownCommitFilter filter =
          new LastKnownCommitFilter(findGitCommitUseCase, gitClient, lastKnownCommit.getName());
      walker.addFilter(filter);
    }

    // only include commits within the project's specified date range
    LocalDate startDate = null;
    LocalDate endDate = null;
    if (project.getVcsStart() != null) {
      startDate = Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(project.getVcsStart());
    }
    if (project.getVcsEnd() != null) {
      endDate = Jsr310Converters.DateToLocalDateConverter.INSTANCE.convert(project.getVcsEnd());
    }
    DateRangeCommitFilter filter = new DateRangeCommitFilter(startDate, endDate);
    walker.addFilter(filter);

    PersistingCommitProcessor commitProcessor =
        new PersistingCommitProcessor(saveCommitPort, project);
    updateProjectPort.update(project);
    walker.walk(new WalkCommitsCommand(gitClient, commitProcessor));
    gitClient.getRepository().close();
    logger.info(
        "scanned {} new commits for project {}", commitProcessor.getUpdatedCommitsCount(), project);
  }
}
