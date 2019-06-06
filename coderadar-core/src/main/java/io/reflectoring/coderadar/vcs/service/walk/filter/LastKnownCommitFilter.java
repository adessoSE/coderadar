package io.reflectoring.coderadar.vcs.service.walk.filter;

import io.reflectoring.coderadar.vcs.port.driver.walk.findCommit.FindGitCommitCommand;
import io.reflectoring.coderadar.vcs.port.driver.walk.findCommit.FindGitCommitUseCase;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;

/** A filter that walks only those commits that are newer than a specified commit. */
public class LastKnownCommitFilter implements CommitWalkerFilter {

  private final RevCommit lastKnownCommit;

  /**
   * Constructor.
   *
   * @param findGitCommitUseCase AAAAAAAAAAA
   * @param gitClient the git client pointing to the local git repository.
   * @param lastKnownCommitName the name of the last known commit. Only commits newer than this
   *     commits are walked. If the name is null, all commits will be walked. If a commit with the
   *     specified name does not exist, an {@link IllegalArgumentException} is thrown.
   */
  @Autowired
  public LastKnownCommitFilter(
      FindGitCommitUseCase findGitCommitUseCase, Git gitClient, String lastKnownCommitName) {
    if (lastKnownCommitName != null) {
      lastKnownCommit =
          findGitCommitUseCase.findCommit(new FindGitCommitCommand(gitClient, lastKnownCommitName));
      if (lastKnownCommit == null) {
        throw new IllegalArgumentException(
            String.format("Last known commit with name %s does not exist!", lastKnownCommitName));
      }
    } else {
      lastKnownCommit = null;
    }
  }

  @Override
  public boolean shouldBeProcessed(RevCommit commit) {
    return this.lastKnownCommit == null || isNewerThan(commit, lastKnownCommit);
  }

  private boolean isNewerThan(RevCommit commit, RevCommit referenceCommit) {
    return commit.getCommitTime() > referenceCommit.getCommitTime();
  }
}
