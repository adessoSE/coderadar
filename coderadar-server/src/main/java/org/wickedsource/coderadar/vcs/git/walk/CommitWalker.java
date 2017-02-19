package org.wickedsource.coderadar.vcs.git.walk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.wickedsource.coderadar.vcs.git.walk.filter.CommitWalkerFilter;

/** A RepositoryWalker that walks through a certain set of commits of a repository. */
public class CommitWalker {

  private List<CommitWalkerFilter> filters = new ArrayList<>();

  /**
   * Walks through all commits in the specified git repository and passes the commits into the
   * specified {@link CommitProcessor} for processing.
   *
   * @param gitClient git client defining the git repository.
   * @param commitProcessor the processor to process each commit.
   */
  public void walk(Git gitClient, CommitProcessor commitProcessor) {
    try {

      final Counter currentSequenceNumber = new Counter(getCommitCount(gitClient));

      ObjectId head = gitClient.getRepository().resolve(Constants.HEAD);
      Iterable<RevCommit> iterator = gitClient.log().add(head).call();
      iterator.forEach(
          commit -> {
            if (shouldBeProcessed(commit)) {
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

  private boolean shouldBeProcessed(RevCommit commit) {
    for (CommitWalkerFilter filter : this.filters) {
      if (!filter.shouldBeProcessed(commit)) {
        return false;
      }
    }
    return true;
  }

  public void addFilter(CommitWalkerFilter filter) {
    this.filters.add(filter);
  }
}
