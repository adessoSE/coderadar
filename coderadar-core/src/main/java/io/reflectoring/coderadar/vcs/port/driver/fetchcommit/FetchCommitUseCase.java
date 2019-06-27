package io.reflectoring.coderadar.vcs.port.driver.fetchcommit;

import io.reflectoring.coderadar.vcs.UnableToFetchCommitException;

public interface FetchCommitUseCase {

  /**
   * Fetches and checks out a commit from a remote repository.
   *
   * @param command A FetchCommitCommand containing the needed arguments.
   * @throws UnableToFetchCommitException Thrown if there is a problem while fetching the commit.
   */
  void fetchCommit(FetchCommitCommand command) throws UnableToFetchCommitException;
}
