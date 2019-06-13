package io.reflectoring.coderadar.vcs.port.driven;

import io.reflectoring.coderadar.vcs.UnableToFetchCommitException;
import io.reflectoring.coderadar.vcs.port.driver.fetchcommit.FetchCommitCommand;

public interface FetchCommitPort {

  /**
   * Fetches and checks out a commit from a remote repository.
   *
   * @param command A FetchCommitCommand containing the needed arguments.
   * @throws UnableToFetchCommitException Thrown if there is a problem while fetching the commit.
   */
  void fetchCommit(FetchCommitCommand command) throws UnableToFetchCommitException;
}
