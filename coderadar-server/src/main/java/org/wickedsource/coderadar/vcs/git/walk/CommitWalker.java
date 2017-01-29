package org.wickedsource.coderadar.vcs.git.walk;

import java.io.IOException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.wickedsource.coderadar.vcs.git.GitCommitFinder;

/** A RepositoryWalker that walks through a certain set of commits of a repository. */
public class CommitWalker {

  private String lastKnownCommitName;

  private GitCommitFinder commitFinder = new GitCommitFinder();

  /**
   * Walks through all commits in the specified git repository and passes the commits into the
   * specified {@link CommitProcessor} for processing.
   *
   * @param gitClient git client defining the git repository.
   * @param commitProcessor the processor to process each commit.
   */
  public void walk(Git gitClient, CommitProcessor commitProcessor) {
    try {

      RevCommit lastKnownCommit = null;
      if (lastKnownCommitName != null) {
        lastKnownCommit = commitFinder.findCommit(gitClient, lastKnownCommitName);
        if (lastKnownCommit == null) {
          throw new IllegalArgumentException(
              String.format("Last known commit with name %s does not exist!", lastKnownCommitName));
        }
      }

      final Counter currentSequenceNumber = new Counter(getCommitCount(gitClient));

      ObjectId head = gitClient.getRepository().resolve(Constants.HEAD);
      Iterable<RevCommit> iterator = gitClient.log().add(head).call();
      RevCommit finalLastKnownCommit = lastKnownCommit;
      iterator.forEach(
          commit -> {
            if (finalLastKnownCommit == null || isNewerThan(commit, finalLastKnownCommit)) {
              RevCommitWithSequenceNumber commitWithSequenceNumber =
                  new RevCommitWithSequenceNumber(commit, currentSequenceNumber.getValue());
              commitProcessor.processCommit(gitClient, commitWithSequenceNumber);
            }
            currentSequenceNumber.decrement();
          });
    } catch (IOException | GitAPIException e) {
      throw new RuntimeException(e);
    }
  }

  private int getCommitCount(Git gitClient) throws IOException, GitAPIException {
    ObjectId head = gitClient.getRepository().resolve(Constants.HEAD);
    Iterable<RevCommit> iterator = gitClient.log().add(head).call();
    Counter count = new Counter(0);
    iterator.forEach(
        commit -> {
          count.increment();
        });
    return count.getValue();
  }

  private boolean isNewerThan(RevCommit commit, RevCommit referenceCommit) {
    return commit.getCommitTime() > referenceCommit.getCommitTime();
  }

  /**
   * Lets you specify the name of the latest known commit that already has been processed. Only
   * commits AFTER the specified commit will be walked by calling {@link #walk(Git,
   * CommitProcessor)}.
   *
   * @param commitName sha1 hash name of the commit.
   */
  public void setLastKnownCommitName(String commitName) {
    this.lastKnownCommitName = commitName;
  }
}
