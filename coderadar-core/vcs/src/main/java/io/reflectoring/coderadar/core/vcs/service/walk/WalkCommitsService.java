package io.reflectoring.coderadar.core.vcs.service.walk;

import io.reflectoring.coderadar.core.vcs.domain.RevCommitWithSequenceNumber;
import io.reflectoring.coderadar.core.vcs.port.driver.walk.walkCommit.WalkCommitsCommand;
import io.reflectoring.coderadar.core.vcs.port.driver.walk.walkCommit.WalkCommitsUseCase;
import io.reflectoring.coderadar.core.vcs.service.walk.filter.CommitWalkerFilter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("WalkCommitsService")
public class WalkCommitsService implements WalkCommitsUseCase {

  private List<CommitWalkerFilter> filters = new ArrayList<>();

  @Override
  public void walk(WalkCommitsCommand command) {
    try {

      final Counter currentSequenceNumber = new Counter(getCommitCount(command.getGitClient()));

      ObjectId head = command.getGitClient().getRepository().resolve(Constants.HEAD);
      Iterable<RevCommit> iterator = command.getGitClient().log().add(head).call();
      iterator.forEach(
          commit -> {
            if (shouldBeProcessed(commit)) {
              RevCommitWithSequenceNumber commitWithSequenceNumber =
                  new RevCommitWithSequenceNumber(commit, currentSequenceNumber.getValue());
              command
                  .getCommitProcessor()
                  .processCommit(command.getGitClient(), commitWithSequenceNumber);
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
