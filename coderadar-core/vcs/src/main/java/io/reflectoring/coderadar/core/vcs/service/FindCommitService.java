package io.reflectoring.coderadar.core.vcs.service;

import io.reflectoring.coderadar.core.vcs.port.driver.walk.findCommit.FindGitCommitCommand;
import io.reflectoring.coderadar.core.vcs.port.driver.walk.findCommit.FindGitCommitUseCase;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.stereotype.Service;

@Service("FindCommitService")
public class FindCommitService implements FindGitCommitUseCase {

  /**
   * Returns the commit with the given name from the given repository. Returns null, if the commit
   * does not exist.
   *
   * @param command The command containing the needed data.
   * @return object representing the commit in question or null if it was not found.
   */
  @Override
  public RevCommit findCommit(FindGitCommitCommand command) {
    try {
      ObjectId commitId = command.getGitClient().getRepository().resolve(command.getCommitName());
      Iterable<RevCommit> commits = command.getGitClient().log().add(commitId).call();
      return commits.iterator().next();
    } catch (MissingObjectException e) {
      return null;
    } catch (Exception e) {
      throw new IllegalStateException(
          String.format(
              "error accessing git repository at %s",
              command.getGitClient().getRepository().getDirectory().getAbsolutePath()),
          e);
    }
  }
}
