package io.reflectoring.coderadar.analyzer.service.filter;

import io.reflectoring.coderadar.analyzer.domain.Commit;
import io.reflectoring.coderadar.vcs.domain.CommitFilter;
import io.reflectoring.coderadar.vcs.port.driver.FindCommitUseCase;
import java.nio.file.Path;

/** A filter that walks only those commits that are newer than a specified commit. */
public class LastKnownCommitFilter implements CommitFilter {

  private final Commit lastKnownCommit;

  /**
   * Constructor.
   *
   * @param lastKnownCommitName the name of the last known commit. Only commits newer than this
   *     commits are walked. If the name is null, all commits will be walked. If a commit with the
   *     specified name does not exist, an {@link IllegalArgumentException} is thrown.
   */
  public LastKnownCommitFilter(
      Path repositoryRoot, FindCommitUseCase findCommitUseCase, String lastKnownCommitName) {
    if (lastKnownCommitName != null) {
      lastKnownCommit = findCommitUseCase.findCommit(repositoryRoot, lastKnownCommitName);
      if (lastKnownCommit == null) {
        throw new IllegalArgumentException(
            String.format("Last known commit with name %s does not exist!", lastKnownCommitName));
      }
    } else {
      lastKnownCommit = null;
    }
  }

  @Override
  public boolean shouldBeProcessed(Commit commit) {
    return this.lastKnownCommit == null || isNewerThan(commit, lastKnownCommit);
  }

  private boolean isNewerThan(Commit commit, Commit referenceCommit) {
    return commit.getTimestamp().after(referenceCommit.getTimestamp());
  }
}
