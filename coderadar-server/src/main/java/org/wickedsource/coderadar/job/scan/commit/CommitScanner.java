package org.wickedsource.coderadar.job.scan.commit;

import java.io.File;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.job.LocalGitRepositoryUpdater;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.vcs.git.walk.CommitWalker;

@Service
public class CommitScanner {

  private Logger logger = LoggerFactory.getLogger(CommitScanner.class);

  private CommitRepository commitRepository;

  private LocalGitRepositoryUpdater updater;

  @Autowired
  public CommitScanner(CommitRepository commitRepository, LocalGitRepositoryUpdater updater) {
    this.commitRepository = commitRepository;
    this.updater = updater;
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
    Git gitClient = updater.updateLocalGitRepository(project);
    scanLocalRepository(project, gitClient);
    return gitClient.getRepository().getDirectory();
  }

  private void scanLocalRepository(Project project, Git gitClient) {
    Commit lastKnownCommit =
        commitRepository.findTop1ByProjectIdOrderBySequenceNumberDesc(project.getId());
    CommitWalker walker = new CommitWalker();
    if (lastKnownCommit != null) {
      walker.setLastKnownCommitName(lastKnownCommit.getName());
    }
    PersistingCommitProcessor commitProcessor =
        new PersistingCommitProcessor(commitRepository, project);
    walker.walk(gitClient, commitProcessor);
    gitClient.getRepository().close();
    logger.info(
        "scanned {} new commits for project {}", commitProcessor.getUpdatedCommitsCount(), project);
  }
}
